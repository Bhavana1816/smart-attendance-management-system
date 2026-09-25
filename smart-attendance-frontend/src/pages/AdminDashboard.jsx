import { useEffect, useState } from "react";
import Navbar from "../components/Navbar";
import api from "../services/api";

function AdminDashboard() {

    const [lowAttendance, setLowAttendance] =
        useState([]);

    useEffect(() => {

        loadReport();

    }, []);

    const loadReport = async () => {

        const response =
            await api.get(
                "/reports/low-attendance"
            );

        setLowAttendance(
            response.data
        );
    };

    return (
        <>
            <Navbar />

            <div className="dashboard">

                <h1>
                    Low Attendance Report
                </h1>

                <div className="card">

                    <h3>
                        Students Below 75%
                    </h3>

                    <p>
                        {lowAttendance.length}
                    </p>

                </div>

                <table>

                    <thead>

                        <tr>
                            <th>Student</th>
                            <th>Register Number</th>
                            <th>Subject</th>
                            <th>Total</th>
                            <th>Present</th>
                            <th>Percentage</th>
                        </tr>

                    </thead>

                    <tbody>

                        {lowAttendance.map(
                            (item, index) => (

                                <tr key={index}>

                                    <td>
                                        {
                                            item.studentName
                                        }
                                    </td>

                                    <td>
                                        {
                                            item.registerNumber
                                        }
                                    </td>

                                    <td>
                                        {
                                            item.subject
                                        }
                                    </td>

                                    <td>
                                        {
                                            item.totalClasses
                                        }
                                    </td>

                                    <td>
                                        {
                                            item.presentClasses
                                        }
                                    </td>

                                    <td>
                                        {
                                            item.percentage
                                        }%
                                    </td>

                                </tr>

                            )
                        )}

                    </tbody>

                </table>

            </div>
        </>
    );
}

export default AdminDashboard;