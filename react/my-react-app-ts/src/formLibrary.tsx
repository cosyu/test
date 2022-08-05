import React from "react";
import Select from "react-select";
import { useForm, Controller } from "react-hook-form";
import Input from "@material-ui/core/Input";

const FormLibrary = () => {

    const myForm = useForm({
        defaultValues:{
            firstName:'',
            select:{}
        }
    });

    //console.log("control");
   // console.log(control);
   // console.log("handleSubmit");
   // console.log(handleSubmit);
    myForm.setValue("firstName","Jason Yu");//set value to field of form
    console.log("getFieldState");
    console.log(myForm.getFieldState("firstName"));
    console.log("control");
    console.log(myForm.control);

    const onSubmit = (data:any) => {
        console.log(data);
    }

    return (
        <>
            <form onSubmit={myForm.handleSubmit(onSubmit)}>
                <Controller 
                    name="firstName" // the filed name needs to be the values defined above
                    control={myForm.control}
                    render={({field})=> <Input {...field} />}
                />
                <Controller 
                    name="select"
                    control={myForm.control}
                    render = {({field})=> <Select  
                     {...field}
                     options={[
                        {value:"1",label:"1"},
                        {value:"2",label:"2"},
                        {value:"3",label:"3"}
                     ]}
                    /> }
                />
                <input type="submit" />
            </form>
        </>
    )
};

export default FormLibrary;