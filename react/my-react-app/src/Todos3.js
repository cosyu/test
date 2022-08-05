import {memo} from "react";

//Every time a component re-renders, its functions get recreated. Because of this, the addTodo function has actually changed
const Todos3 = ({todos, addTodo}) => {
    console.log("child render...");
  return (
    <>
      <h2>My Todos</h2>
      {todos.map((todo, index) => {
        return <p key={index}>{todo}</p>;
      })}
      <button onClick={addTodo}>Add Todo</button>
    </>
  );
};

export default memo(Todos3);