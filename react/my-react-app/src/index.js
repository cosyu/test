import React from 'react';
import ReactDOM from 'react-dom/client';
import Car2 from './Car2.js';//import component
import Todos from './Todos.js';
import Todos3 from './Todos3.js';
import {useState,useEffect,createContext,useContext,useReducer,useCallback } from 'react';//import useState hook to manage the input

//JSX is an extension of the JavaScript language based on ES6, and is translated into regular JavaScript at runtime.

//<></> is fragment to wrap multiple html
let e1 = (<><h1>Hello React!</h1></>);//react will throw error if the html is incorrect

//use {} to execute expression in JSX
const v = 10;
e1 = <h1>Hello JSX!{v<10 ? "hello" : "Bye"}</h1>;

//e1 = <input type="text" />;//element MUST be closed

//create function component
function Car(props1){//Props are like function arguments
    //return <h3>This is car component,{props1.color}</h3>; // get value
    return <h3>This is car component,{props1.brand.model}</h3>; // get object
}

//Use the Car component inside the Garage component, it can use Porps to pass argument to another component
function Garage(){

    const carInfo = {name:"Ford",model:"Mustang"};
/*
    //pass a value
    return (
        <>
            <h1>Who lives in my Garge?</h1>
            <Car color="green" />
        </>
    );*/

    //pass a object
    return (
        <>
            <h1>Who lives in my Garge?</h1>
            <Car brand={carInfo} />
        </>
    );
}

const root = ReactDOM.createRoot(document.getElementById('root'));
//root.render(e1);

const root2 = ReactDOM.createRoot(document.getElementById('root2'));
root2.render(<Car color="red" />);//use function component, use an attribute to pass a color to the Car component
root2.render(<Garage />);
root2.render(<Car2 />);//use the imported component

//event
function Football(){

    //no parameter
  /*
    const shoot = ()=>{
        alert("Great shoot");
    }

    return (
        <button onClick={shoot}>Take the shot!</button>
    );
*/
    //pass parameter
    const shoot = (a,b) =>{
        alert(b.type);
    }

    //onClick instead of onclick, onClick={shoot} instead of onClick="shoot()"
    return (
        <button onClick={(event)=>shoot("Goal!",event)}>Take the shot!</button>
    );

}

root2.render(<Football />);

//conditional
function Goal(props){

    const isGoal = props.isGoal;
  /*  
    if(isGoal){
        return <h1>Goal</h1>;
    }else{
        return <h1>Miss</h1>;
    }
*/
/*
    return (
        <>
            {isGoal ? <h1>Goal</h1> : <h1>Miss</h1>}
        </>
    );
*/
    return (
        <>
            { isGoal && <h1>Goal</h1>}
        </>
    );
}
root2.render(<Goal isGoal={true} />);


//list
const cars = ['Ford','BMW','Audi'];
root2.render(
    <>
        <ul>
            {cars.map((car)=><li>{car}</li>)}
        </ul>
    </>
);
//key, if an item is updated or removed, only that item will be re-rendered instead of the entire list
const carMap = [];
carMap[0] = {id:1,brand:'Ford'};
carMap[1] = {id:2,brand:'BMW'};
carMap[2] = {id:3,brand:'Audi'};

root2.render(
    <>
        {carMap.map((car)=><li>{car.id}-{car.brand}</li>)}
    </>
);

//The React useState Hook allows us to track state in a function component.
//form, In React, form data is usually handled by the components.
//When the data is handled by the components, all the data is stored in the component state.
function MyForm(){
    const [name1,setName1] = useState("");//init with empty string, name1 is variable, setName1 is function
    const [textarea1, setTextarea1] = useState("The content of a textarea goes in the value attribute");//init with string which will be assiged to the element which use textarea1
    const [myCar,setMyCar] = useState("Volvo");

    const handleSubmit = (event) => {
        event.preventDefault();
        //window.alert(`The name you input was:${name1}`);
    }

    const handleChange = (event) =>{
        setTextarea1(event.target.value);
        console.log(event.target.value);
    }

    const handleChange2 = (event) =>{
        setMyCar(event.target.value);
        console.log(event.target.value);
    }
    /*
    HTML:
    <textarea>
        Content of the textarea.
    </textarea>

    React:
    <textarea value="Content of the textarea." />
  
    HTML:
    <select>
        <option value="Ford">Ford</option>
        <option value="Volvo" selected>Volvo</option>
        <option value="Fiat">Fiat</option>
    </select>

    React:
    The selected value is defined with a value attribute on the select tag

    */ 
    return(
        <form onSubmit={handleSubmit}>
            <label>Enter your name:</label>
            <input type="text" value={name1} 
            onChange={(e)=> setName1(e.target.value)} /> <br />
            <textarea value={textarea1} onChange={handleChange} />
            <input type="submit" /><br/>
            <select value={myCar} onChange={handleChange2} >
                <option value="Ford">Ford</option>
                <option value="Volvo">Volvo</option>
                <option value="Fiat">Fiat</option>
            </select>
        </form>
    );
}
root2.render(<MyForm />);

function MyForm2(){
    const [inputs,setInputs] = useState({});//init with empty object

    const handleChange = (event) =>{
        const name = event.target.name;
        const value = event.target.value;
        setInputs(values1 => ({...values1,[name]:value}));//values is function name,
    }

    const handleSubmit = (event) => {
        event.preventDefault();
        //window.alert(inputs);
        console.log(inputs);
    }
    
    return(
        <form onSubmit={handleSubmit}>
            <label>Enter your name:</label>
            <input type="text" name="username" value={inputs.username || ""}
            onChange={handleChange} /><br/>

            <label>Enter your age:</label>
            <input type="number" name="age" value={inputs.age || ""} 
            onChange={handleChange} />
            <input type="submit" />
        </form>
    );
}
//root2.render(<MyForm2 />);

//memo,using memo will cause React to skip rendering a component if its props have not change

const App = () => {
    const [count,setCount] = useState(0);//count is variable, setCount is function, this is using Destructuring to retrieve specify variable from array
    const [todos,setTodos] = useState(["todo1","todo2"]);//init array object for todos

    const increment = () =>{
        setCount((c) => c+1);
    };

    //without memo, When you click the increment button(change the {count in the whole JSX section}), the Todos component re-renders (as it is inside the whole JSX section)
    //If this component was complex, it could cause performance issues.
    return(
        <>
            <Todos todos={todos} /><hr/>
            <div>
                Count: {count}
                <button onClick={increment}>+</button>
            </div>
        </>
    );
}
root2.render(<App />);


function Car3(){
    const [car,setCar] = useState({
        brand: "Ford",
        model: "Mustang",
        year: "1964",
        color: "red"
    });

    const updateColor = () =>{
        //set function in useState always returns object
        setCar((prevState) =>{ //create anonymous function, prevState is function parameter(car object)
            console.log(prevState);
			//change the property of object, it will case re-render 
            return {...prevState,color:"blue",year:"1970"} //...preState means use all properties(may be many) of the object, you can update the specify property.e.g. color:"bule"
        });
    }

    return(
        <>
            <h3>My {car.brand}</h3>
            <p>
                It is a {car.color} {car.model} from {car.year}.
            </p>
            <button type='button' onClick={updateColor}>Blue</button>
        </>
    );

}
root2.render(<Car3 />);

//useEffect,perform side effect
function Timer(){
    const[count,setCount] = useState(0);

    //useEffect runs on every render. That means that when the count changes, a render happens, which then triggers another effect.
    //useEffect(<function>,<dependency>); the dependency is optional
    useEffect(()=>{ // create anonymous function without parameter, function can be parameter for another function in JS
           let timer = setTimeout(()=>{//create anonymous function without parameter
                setCount((count)=>count+1);//create anonymous function with parameter
            },1000);
            console.log("re-render");
            return () => {clearTimeout(timer)}; //it is good practice to clear timeout function after use
        }
    ,[]);//<- add empty array as dependency which will re-render the component if it is changed
    //without the dependency, the function will change {count} after 1 second which will lead to re-render the component

    
    return <h3>I've rendered {count} times!</h3>
}
root2.render(<Timer />);

//context object,is a way to manage state globally.
const UserContext = createContext();

//create component which will use the value/object of a context object
function Component1(){
    const [user,setUser] = useState("Jason Yu");//user is string type and init with 'Jason Yu'

    //console.log(setUser);

    //use the Context Provider to wrap the tree of components that need the state Context.
    //set the value to context
    return (
        <UserContext.Provider value={user}>
            <h3>{`Hello ${user}`}</h3>
            <Component2 />
        </UserContext.Provider>
    );
}

function Component2(){

    const user1 = useContext(UserContext);//get the object from context object

    return (
        <>
            <h3>Component2</h3>
            <h3>{`Hello ${user1} again!`}</h3>
        </>
    );
}
root2.render(<Component1 />);

//useReducer,it is similar to the useState Hook.It allows for custom state logic.
const initTodos = [
    {id:1,title:"Todo 1",complete:false},
    {id:2,title:"Todo 2",complete:false}
];


const reducer = (state,action) => {

    console.log(state);//it is previous object state
    console.log(action);//{type : 'COMPLETE', id:1}
    switch(action.type){
        case "COMPLETE":
            //return a new state array
            return state.map((todo)=>{
                if(todo.id === action.id){
                    //it returns todo object
                    //...todo means all properties, complete : !todo.complete means it change the complete property
                    return {...todo,complete : !todo.complete}
                }else{
                    return todo;
                }
            });
        default:
            return state;
    }
};

function Todos2(){

    //useReducer(<reducer>, <initialState>)
    //The reducer function contains your custom state logic and the initialStatecan be a simple value but generally will contain an object.
    //The useReducer Hook returns the current state and a dispatchmethod.
    const[todos,dispatch] = useReducer(reducer,initTodos);

    const handleComplete = (todo) =>{
        //this will trigger function in useReducer , i.e. reducer function
        dispatch({type:"COMPLETE",id:todo.id});//parameter is action of reducer function, use todos as state parameter of reducer function
    }

    //for loop returned todos and display property
    return (
        <>
            {
                todos.map((todo) => (
                    <div key={todo.id}>
                        <input type="checkbox" checked={todo.complete} 
                        onChange={()=> handleComplete(todo)} /> {todo.title}
                    </div>
                ))
            }
        </>
    );
}
root.render(<Todos2 />);


//useCallback, it allows us to isolate resource intensive functions so that they will not automatically run on every render
const App3 = () =>{
    const [count, setCount] = useState(0);
    const [todos, setTodos] = useState([]);

    const increment = () => {
        setCount((c) => c + 1);
    };

    const addTodo = useCallback(() => {
        setTodos((t) => [...t,["New Todo"]]);
    },[todos])

    return (
        <>
          <Todos3 todos={todos} addTodo={addTodo} />
          <hr />
          <div>
            Count: {count}
            <button onClick={increment}>+</button>
          </div>
        </>
      );
}

root.render(<App3 />);
