import React from "react";

export declare type UserObject = {
    username:string;
    password:string;
    age?:number;//optional
}

export declare type ControlDisplay = boolean | "hidden";

export type MyHandler = (v1:string,v2:string,v3?:string) => void;


