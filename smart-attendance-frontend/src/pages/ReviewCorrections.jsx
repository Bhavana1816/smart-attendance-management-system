import { useEffect, useState } from "react";
import Navbar from "../components/Navbar";
import api from "../services/api";

function ReviewCorrections() {

    const [requests, setRequests] =
        useState([]);

    useEffect(() => {

        loadRequests();

    }, []);

    const loadRequests = async () => {

        const response =
            await api.get(
                "/corrections/pending"
            );

        setRequests(response.data);
    };

    const approve = async id => {

        await api.put(
            `/corrections/${id}/approve`
        );

        alert(
            "Correction approved"
        );

        loadRequests();
    };

    const reject = async id => {

        await api.put(
            `/corrections/${id}/reject`
        );

        alert(
            "Correction rejected"
        );

        loadRequests();
    };

    return (
        <>
            <Navbar />

            <div className="dashboard">

                <h1>
                    Correction Requests
                </h1>

                <table>

                    <thead>

                        <tr>
                            <th>Student</th>
                            <th>Subject</th>
                            <th>Old</th>
                            <th>New</th>
                            <th>Reason</th>
                            <th>Action</th>
                        </tr>

                    </thead>

                    <tbody>

                        {requests.map(
                            request => (

                                <tr
                                    key={
                                        request.id
                                    }
                                >

                                    <td>
                                        {
                                            request.studentName
                                        }
                                    </td>

                                    <td>
                                        {
                                            request.subject
                                        }
                                    </td>

                                    <td>
                                        {
                                            request.oldStatus
                                        }
                                    </td>

                                    <td>
                                        {
                                            request.newStatus
                                        }
                                    </td>

                                    <td>
                                        {
                                            request.reason
                                        }
                                    </td>

                                    <td>

                                        <button
                                            onClick={() =>
                                                approve(
                                                    request.id
                                                )
                                            }
                                        >
                                            Approve
                                        </button>

                                        <button
                                            onClick={() =>
                                                reject(
                                                    request.id
                                                )
                                            }
                                        >
                                            Reject
                                        </button>

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

export default ReviewCorrections;