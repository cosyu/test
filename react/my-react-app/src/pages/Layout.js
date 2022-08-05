import {Outlet,Link} from "react-router-dom";

/**
 * The Layout component has <Outlet> and <Link> elements.
 * The <Outlet> renders the current route selected.
 * <Link> is used to set the URL and keep track of browsing history.
 * Anytime we link to an internal path, we will use <Link> instead of <a href="">.
 * 
 * /blogs means the url is http://localhost:3000/blogs which is defined in the Route of index.js
*/

const Layout = () => {
    return (
        <>
            <nav>
                <ul>
                    <li ><Link to="/">Home</Link></li>
                    <li ><Link to="/blogs">Blogs</Link></li>
                    <li ><Link to="/contact">Contact</Link></li>
                    <li ><Link to="/xxx">Other</Link></li>
                </ul>
            </nav>
            <Outlet />
        </>
    )
};

export default Layout; // export for reusing