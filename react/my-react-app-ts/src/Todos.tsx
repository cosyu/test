
import {memo} from "react";

const Todos = (props : any) => {

    console.log("child Todos render");
    return(
        <>
            <h3>My Todos</h3>
            {
                props.todos.map((todo : any,index: any)=>{
                    return <p key={index}>{todo}</p>
                })
            }
            <button onClick={props.addTodo}>Add Todo</button>
        </>
    );
}

export default memo(Todos);