import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

import Navbar from "../components/Navbar";
import api from "../services/api";

function FacultyDashboard() {

    const [assignments, setAssignments] =
        useState([]);

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

    return (
        <>
            <Navbar />

            <div className="dashboard">

                <h1>Faculty Dashboard</h1>

                <div className="cards">

                    <div className="card">

                        <h3>Assigned Classes</h3>

                        <p>
                            {assignments.length}
                        </p>

                    </div>

                </div>

                <h2>
                    My Subjects
                </h2>

                <table>

                    <thead>

                        <tr>
                            <th>Subject</th>
                            <th>Code</th>
                            <th>Section</th>
                            <th>Semester</th>
                        </tr>

                    </thead>

                    <tbody>

                        {assignments.map(item => (

                            <tr key={
                                item.subjectId +
                                "-" +
                                item.sectionId
                            }>

                                <td>
                                    {item.subjectName}
                                </td>

                                <td>
                                    {item.subjectCode}
                                </td>

                                <td>
                                    {item.sectionName}
                                </td>

                                <td>
                                    {item.semester}
                                </td>

                            </tr>

                        ))}

                    </tbody>

                </table>

                <div className="actions">

                    <Link to="/mark-attendance">
                        Mark Attendance
                    </Link>

                    <Link to="/corrections">
                        Review Corrections
                    </Link>

                    <Link to="/admin">
                        Low Attendance Report
                    </Link>

                </div>

            </div>
        </>
    );
}

export default FacultyDashboard;