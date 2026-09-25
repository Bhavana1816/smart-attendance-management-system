import {
    BrowserRouter,
    Routes,
    Route
} from "react-router-dom";

import Login from "./pages/Login";
import StudentDashboard from "./pages/StudentDashboard";
import AttendanceHistory from "./pages/AttendanceHistory";
import FacultyDashboard from "./pages/FacultyDashboard";
import MarkAttendance from "./pages/MarkAttendance";
import ReviewCorrections from "./pages/ReviewCorrections";
import AdminDashboard from "./pages/AdminDashboard";

function App() {

    return (

        <BrowserRouter>

            <Routes>

                <Route
                    path="/"
                    element={<Login />}
                />

                <Route
                    path="/student"
                    element={
                        <StudentDashboard />
                    }
                />

                <Route
                    path="/history"
                    element={
                        <AttendanceHistory />
                    }
                />

                <Route
                    path="/faculty"
                    element={
                        <FacultyDashboard />
                    }
                />

                <Route
                    path="/mark-attendance"
                    element={
                        <MarkAttendance />
                    }
                />

                <Route
                    path="/corrections"
                    element={
                        <ReviewCorrections />
                    }
                />

                <Route
                    path="/admin"
                    element={
                        <AdminDashboard />
                    }
                />

            </Routes>

        </BrowserRouter>
    );
}

export default App;
