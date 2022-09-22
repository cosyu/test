import React, { useCallback } from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { UserObject,MyHandler } from './Data';
import useMyFetch from './useMyFetch';
import {useState,useEffect,createContext,useContext,useReducer,useRef } from 'react';
import { useParams, useNavigate, useLocation } from "react-router-dom";
import { useForm } from "react-hook-form";
import FormLibrary from './formLibrary';
import Todos from './Todos';
import { v4 as uuid } from "uuid";

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);
/*
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
*/
// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
//reportWebVitals();

const user : UserObject = {
  username:"Jason",
  password:"123"
}

console.log(user);

const v1:string = "a";
const v2:string = "b";
const v3 = "c";
const v4:number = 10;
const myHandler1 : MyHandler = (v1,v2) =>{
    return v1+v2;
}

console.log(myHandler1);


//React.FC, to create component with generic
declare type Props1 = {
  id : string;
  name : string;
}
const Component5 : React.FC<Props1> = (props) =>{

  return(
    <>
      <div>{props.id} + {props.name}</div>
    </>
  );
}

const v11 : Props1 = {id:"123",name:"Jason"};

root.render(<Component5 {...v11} />);

//Conditional Rendering
function Garage(props:any){
    const cars = props.cars;

    //it uses && for conditional rendering
    return(
      <>
        <h3>Garage</h3>
        {cars.length >0 && 
          <h3>You have {cars.length} cars in your Garage!</h3>
        }
      </>
    );
}

const cars = ['Ford', 'BMW', 'Audi'];
root.render(<Garage cars={cars} />);

function MyForm(){

  const [showCreateForm, setIsShowCreateForm] = useState(false);
  const toggleCreateForm = () => setIsShowCreateForm(!showCreateForm);
  const v = toggleCreateForm();
  //console.log(toggleCreateForm());
  return (
    <>
      <div>My Form {showCreateForm}</div>
    </>
    
  );
}

//root.render(<MyForm />);

//define a method
export type CustomButtonHandler = (v1: string , v2: string) => void;

//implement method 
const f1 : CustomButtonHandler = (v1,v2) => {
  console.log(v1+v2);
}
//call method
f1("123","456");

//if
let arr1: string[] = [];
if(arr1){
  console.log("arr1 is init");
}
let v12;
if(v12){
   console.log("var12 is init");
}
let v13 = null;
if(v13){
  console.log("var13 is init");
}

//! x! 将从 x 值域中排除 null 和 undefined 。
//userpoolContext.currUserPool!.name
let obj1 ;
console.log(typeof obj1);//undefined
//console.log(obj1?.name);//undefined
//console.log(obj1!.name);//error, cannot read property name
obj1 = {name:"Jason"};
//obj1 = null;
console.log(obj1?.name);//Jason
console.log(obj1!.name);//Jason

//?.可选链（Optional Chaining）。有了可选链后，我们编写代码时如果遇到 null 或 undefined 就可以立即停止某些表达式的运行
//currUserPool.userPoolMemberList?.find(...)
let obj2:string[] = [];
console.log(obj2?.[0]);//undefined
if(!obj2?.[0]){
  console.log("test ?.");
}

//& 运算符
//通过 & 运算符可以将现有的多种类型叠加到一起成为一种类型，它包含了所需的所有类型的特性
/*export declare type CheckboxConfig = {
    type: "checkbox";
    props: CheckboxProps;
} & BaseReviewFieldConfig;
*/
type P1 = {x: number;}
type P2 = P1 & {y:number;};
let p : P2 = {
  x:1,
  y:2
}
console.log(p);

//??, 当左侧操作数为 null 或 undefined 时，其返回右侧的操作数，否则返回左侧的操作数
const foo = null ?? "defautl string";
console.log(foo);

declare type Customer = {
  name : string;
  city?: string;
}

let c1 : Customer = {
  name:"Jason"
}

let c1City = c1?.city ?? "unknown city";
console.log(c1City);

//as const
const v21 = {
  key1: ['ascend' as const, 'descend' as const]
}
v21.key1.push("ascend");//ok
//v21.key1.push("ascend1");//error

console.log(v21.key1);
let v22 = 'ascend' as const;
//v22 = "abc"; // readonly cannot be changed
console.log(v22);

//When a const assertion is used, the array becomes a readonly tuple
const arr2 = [3,4] as const;
// ⛔️ ERROR: Property 'push' does not exist on type
//  'readonly [3, 4]'
//arr2.push(5);

//

let DropdownList2: React.FC & {key1:string};
let DropdownList3: React.FC & [];//?

// use customer hook
const CustomerHook = () => {
  const [data] = useMyFetch("https://jsonplaceholder.typicode.com/todos");

  return(
    <>
      {
        data &&
          data.map((item : any)=>{
            return <p key={item.id}>{item.title}</p>
          })
      }
    </>
  );
}

root.render(<CustomerHook />);

//useForm
export default function MyForm2() {
  const { register, handleSubmit, watch, formState: { errors } } = useForm();
  const onSubmit = (data2 : any) => {
    console.log(data2);
  }

  console.log(watch("example")); // watch input value by passing the name of it

  return (
    <form onSubmit={handleSubmit(onSubmit)}>
      <input {...register("firstName", { required: true, maxLength: 20 })} />
      {errors.firstName && <span>This field is required and max length is 20</span>}
      <input {...register("lastName", { pattern: /^[A-Za-z]+$/i })} />
      {errors.lastName && <span>This field is required and only A-z is allowed</span>}
      <input type="number" {...register("age", { min: 18, max: 99 })} />
      <input type="submit" />
    </form>
  );
}

root.render(<MyForm2 />);

root.render(<FormLibrary />);

//useRef
function MyRef(){

  const [inputValue,setInputValue] = useState("");
  const count = useRef(0);
  const v1 = useRef("test");
  console.log("count:"+count);
  console.log("count.current:"+count.current);
  console.log("v1.current:"+v1.current);

  const req_id = useRef<string>(uuid()).current;
  console.log("req_id:"+req_id);
  useEffect(()=>{
    console.log("useEffect...");
    count.current = count.current + 1;
  },[count.current]);//dependency is [], useEffect will run only on the first render, dependency is [count.current], useEffect will run any dependency value changes
  //setInputValue(e.target.value)
  //change inputValue, it will re-render
  return (
    <>
      <input type="text" value={inputValue}
        onChange={(e)=> setInputValue(e.target.value) } />
        <h3>Render Count:{count.current}</h3>
    </>
  );

}

root.render(<MyRef />);

//useCallback & useRef & useParams
const MyCallBack = () =>{

  const [count, setCount] = useState(0);
  const [todos, setTodos] = useState([""]);
  const req_id = useRef<string>(uuid()).current;//useRef to make the value will not be changed even the component re-renders.
  const { parameter1 } = useParams<{ parameter1: string }>();
  //const location = useLocation(); it only can be used in the context of a <Router> component.

  const increment = () => {
    setCount((c) => c + 1);
  };


  const addTodo = useCallback(()=>{ //defind a function with callback
    console.log("use call back run");
    setTodos((t)=> [...t,"New Todo"]);
  },[todos]);//The useCallback Hook only runs when one of its dependencies update, i.e. [todos]

  //children changes todos which will cause parent re-render.
  console.log("the parent renders...");
  console.log("req_id:"+req_id);
  console.log("parameter1:"+parameter1);
  //console.log("location:");
  //console.log(location);

  return (
    <>
      <Todos todos={todos} addTodo={addTodo} />
      <hr />
      <div>
        Count: {count}
        <button onClick={increment}>+</button>
      </div>
    </>
  );
}

root.render(<MyCallBack />);

//define the customer object
let isLoading2: { [reqId: string]: boolean };//isLoading2 is object, reqId is key name , typeof is string, value is boolean
isLoading2 = {reqId:true,reqId2:true,"reqId3":true,123:true};
console.log(isLoading2);
Object.entries(isLoading2).map(([k, v]) => {
    console.log("key typeof:" + typeof k + " value:" + k);
    console.log("value typeof:" + typeof v + " value:" + v);
})

/**


useForm,useRef

!delegateSubList

axios

declare module

react-bootstrap

antd

 a.companyIdentityNo.localeCompare

 <a key="" data-id="" />

 html <Fragment></Fragment>
 
 useParams

*/ 