import React from "react";
import ReactDom from "react-dom/client";
import {BrowserRouter,Routes,Route} from "react-router-dom";
import Layout from "./pages/Layout"; // ./ means current folder
import Home from "./pages/Home";
import Blogs from "./pages/Blogs";
import Contact from "./pages/Contact";
import NoPage from "./pages/NoPage";

export default function App(){

    /*
    <Route>s can be nested. The first <Route> has a path of / and renders the Layout component.

The nested <Route>s inherit and add to the parent route. So the blogs path is combined with the parent and becomes /blogs.

The Home component route does not have a path but has an index attribute. That specifies this route as the default route for the parent route, which is /.

Setting the path to * will act as a catch-all for any undefined URLs. This is great for a 404 error page.
    */
    return(
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Layout />}>
                    <Route index element={<Home />}></Route>
                    <Route path="blogs" element={<Blogs />}></Route>
                    <Route path="contact" element={<Contact />}></Route>
                    <Route path="*" element={<NoPage />}></Route>
                </Route>
            </Routes>
        </BrowserRouter>
    );
}

const root = ReactDom.createRoot(document.getElementById("root"));
root.render(<App />);