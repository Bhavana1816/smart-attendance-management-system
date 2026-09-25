import { useNavigate } from "react-router-dom";

function Navbar() {

    const navigate = useNavigate();

    const name =
        localStorage.getItem("name");

    const logout = () => {

        localStorage.clear();

        navigate("/");
    };

    return (
        <nav className="navbar">

            <h2>Smart Attendance</h2>

            <div>
                <span>
                    Welcome, {name}
                </span>

                <button
                    onClick={logout}
                >
                    Logout
                </button>
            </div>

        </nav>
    );
}

export default Navbar;