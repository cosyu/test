
import exp from "constants";
import {useState,useEffect} from "react";

//create customer hook
const useMyFetch = (url:string) =>{

    const [data,setData] = useState([]);

    useEffect(()=>{
        fetch(url).then(res=>{console.log(res)}).then(data1=>console.log(data1));
    },[url]);

    return [data];
}

export default useMyFetch;