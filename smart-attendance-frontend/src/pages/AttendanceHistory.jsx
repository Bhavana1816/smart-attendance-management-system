import { useEffect, useState } from "react";
import Navbar from "../components/Navbar";
import api from "../services/api";

function AttendanceHistory() {

    const [history, setHistory] =
        useState([]);

    useEffect(() => {

        loadHistory();

    }, []);

    const loadHistory = async () => {

        const response =
            await api.get(
                "/attendance/student/history"
            );

        setHistory(response.data);
    };

    const requestCorrection = async (
        recordId
    ) => {

        const reason =
            prompt(
                "Why should this attendance be corrected?"
            );

        if (!reason) {
            return;
        }

        try {

            await api.post(
                "/corrections",
                {
                    attendanceRecordId:
                        recordId,

                    newStatus:
                        "PRESENT",

                    reason
                }
            );

            alert(
                "Correction request submitted"
            );

        } catch (error) {

            alert(
                "Could not submit correction"
            );
        }
    };

    return (
        <>
            <Navbar />

            <div className="dashboard">

                <h1>Attendance History</h1>

                <table>

                    <thead>

                        <tr>
                            <th>Date</th>
                            <th>Subject</th>
                            <th>Section</th>
                            <th>Period</th>
                            <th>Status</th>
                            <th>Correction</th>
                        </tr>

                    </thead>

                    <tbody>

                        {history.map(item => (

                            <tr key={item.recordId}>

                                <td>
                                    {item.date}
                                </td>

                                <td>
                                    {item.subject}
                                </td>

                                <td>
                                    {item.section}
                                </td>

                                <td>
                                    {item.period}
                                </td>

                                <td>
                                    {item.status}
                                </td>

                                <td>

                                    {item.status ===
                                        "ABSENT" && (

                                        <button
                                            onClick={() =>
                                                requestCorrection(
                                                    item.recordId
                                                )
                                            }
                                        >
                                            Request Correction
                                        </button>

                                    )}

                                </td>

                            </tr>

                        ))}

                    </tbody>

                </table>

            </div>
        </>
    );
}

export default AttendanceHistory;