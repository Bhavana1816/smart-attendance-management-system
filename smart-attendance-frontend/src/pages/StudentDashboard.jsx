import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import Navbar from "../components/Navbar";
import api from "../services/api";

function StudentDashboard() {

    const [summary, setSummary] =
        useState([]);

    useEffect(() => {

        loadSummary();

    }, []);

    const loadSummary = async () => {

        try {

            const response =
                await api.get(
                    "/reports/student/summary"
                );

            setSummary(response.data);

        } catch (error) {

            console.error(error);
        }
    };

    return (
        <>
            <Navbar />

            <div className="dashboard">

                <h1>Student Dashboard</h1>

                <div className="cards">

                    <div className="card">
                        <h3>Subjects</h3>
                        <p>{summary.length}</p>
                    </div>

                    <div className="card">
                        <h3>Low Attendance</h3>
                        <p>
                            {
                                summary.filter(
                                    item =>
                                        item.lowAttendance
                                ).length
                            }
                        </p>
                    </div>

                </div>

                <div className="actions">

                    <Link to="/history">
                        View Attendance History
                    </Link>

                </div>

                <table>

                    <thead>
                        <tr>
                            <th>Subject</th>
                            <th>Total</th>
                            <th>Present</th>
                            <th>Absent</th>
                            <th>Percentage</th>
                            <th>Status</th>
                        </tr>
                    </thead>

                    <tbody>

                        {summary.map(item => (

                            <tr key={item.subjectId}>

                                <td>
                                    {item.subjectName}
                                </td>

                                <td>
                                    {item.totalClasses}
                                </td>

                                <td>
                                    {item.presentClasses}
                                </td>

                                <td>
                                    {item.absentClasses}
                                </td>

                                <td>
                                    {item.percentage}%
                                </td>

                                <td>
                                    {
                                        item.lowAttendance
                                            ? "Low Attendance"
                                            : "Good"
                                    }
                                </td>

                            </tr>

                        ))}

                    </tbody>

                </table>

            </div>
        </>
    );
}

export default StudentDashboard;