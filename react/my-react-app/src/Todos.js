
import {memo} from "react";//Use memoto keep the Todos component from needlessly re-rendering.

//todos is array parameter
const Todos = ({todos}) =>{
    console.log("child render");
    return (
        <>
            <h3>My Todos</h3>
            {
                todos.map((value,index) => {
                    return <p key={index}>{value}</p>
                })
            }
        </>
    );
}
/*
export declare type UserObject = {
    username: string;
    password: String;
}
*/
export default memo(Todos); //Wrap the Todos component export in memo