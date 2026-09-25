import { useEffect, useState } from "react";
import Navbar from "../components/Navbar";
import api from "../services/api";

function MarkAttendance() {

    const [assignments, setAssignments] =
        useState([]);

    const [selected, setSelected] =
        useState(null);

    const [date, setDate] =
        useState("");

    const [period, setPeriod] =
        useState(1);

    const [students, setStudents] =
        useState([]);

    const [attendance, setAttendance] =
        useState({});

    useEffect(() => {

        loadAssignments();

    }, []);

    const loadAssignments = async () => {

        const response =
            await api.get(
                "/faculty/assignments"
            );

        setAssignments(response.data);
    };

    const startAttendance = async () => {

        if (!selected || !date) {

            alert(
                "Select subject, section and date"
            );

            return;
        }

        try {

            const sessionResponse =
                await api.post(
                    "/attendance/session",
                    {
                        subjectId:
                            selected.subjectId,

                        sectionId:
                            selected.sectionId,

                        attendanceDate:
                            date,

                        periodNumber:
                            Number(period)
                    }
                );

            const sessionId =
                sessionResponse.data;

            const studentsResponse =
                await api.get(
                    `/faculty/students/${selected.sectionId}`
                );

            setStudents(
                studentsResponse.data
            );

            const initialAttendance = {};

            studentsResponse.data.forEach(
                student => {

                    initialAttendance[
                        student.id
                    ] = "PRESENT";

                }
            );

            setAttendance(
                initialAttendance
            );

            localStorage.setItem(
                "sessionId",
                sessionId
            );

        } catch (error) {

            alert(
                error.response?.data?.error ||
                "Unable to start attendance"
            );
        }
    };

    const changeStatus = (
        studentId
    ) => {

        setAttendance(
            previous => ({
                ...previous,

                [studentId]:
                    previous[studentId] ===
                    "PRESENT"
                        ? "ABSENT"
                        : "PRESENT"
            })
        );
    };

    const saveAttendance = async () => {

        const sessionId =
            localStorage.getItem(
                "sessionId"
            );

        const records =
            students.map(student => ({
                studentId:
                    student.id,

                status:
                    attendance[student.id]
            }));

        try {

            await api.post(
                "/attendance/records",
                {
                    sessionId:
                        Number(sessionId),

                    records
                }
            );

            alert(
                "Attendance saved successfully"
            );

        } catch (error) {

            alert(
                error.response?.data?.error ||
                "Unable to save attendance"
            );
        }
    };

    return (
        <>
            <Navbar />

            <div className="dashboard">

                <h1>
                    Mark Attendance
                </h1>

                <div className="form-row">

                    <select
                        onChange={e => {

                            const value =
                                Number(
                                    e.target.value
                                );

                            setSelected(
                                assignments[value]
                            );

                        }}
                    >

                        <option value="">
                            Select Subject
                        </option>

                        {assignments.map(
                            (item, index) => (

                                <option
                                    key={index}
                                    value={index}
                                >
                                    {item.subjectName}
                                    {" - "}
                                    Section {item.sectionName}
                                </option>

                            )
                        )}

                    </select>

                    <input
                        type="date"
                        value={date}
                        onChange={
                            e =>
                                setDate(
                                    e.target.value
                                )
                        }
                    />

                    <input
                        type="number"
                        min="1"
                        max="10"
                        value={period}
                        onChange={
                            e =>
                                setPeriod(
                                    e.target.value
                                )
                        }
                    />

                    <button
                        onClick={
                            startAttendance
                        }
                    >
                        Load Students
                    </button>

                </div>

                {students.length > 0 && (

                    <>

                        <table>

                            <thead>

                                <tr>
                                    <th>Register No</th>
                                    <th>Name</th>
                                    <th>Status</th>
                                </tr>

                            </thead>

                            <tbody>

                                {students.map(
                                    student => (

                                        <tr
                                            key={
                                                student.id
                                            }
                                        >

                                            <td>
                                                {
                                                    student.registerNumber
                                                }
                                            </td>

                                            <td>
                                                {
                                                    student.name
                                                }
                                            </td>

                                            <td>

                                                <button
                                                    onClick={() =>
                                                        changeStatus(
                                                            student.id
                                                        )
                                                    }
                                                >
                                                    {
                                                        attendance[
                                                            student.id
                                                        ]
                                                    }
                                                </button>

                                            </td>

                                        </tr>

                                    )
                                )}

                            </tbody>

                        </table>

                        <button
                            className="save-button"
                            onClick={
                                saveAttendance
                            }
                        >
                            Save Attendance
                        </button>

                    </>

                )}

            </div>
        </>
    );
}

export default MarkAttendance;