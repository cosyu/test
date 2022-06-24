package com.example.annotation;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({ "age"})//it means specify fields will be ignored for serializable or deserialize, same as @JsonIgnore
public class Student {

    private int id;

    @JsonProperty("stuName")//it means the filed will be stuName for serializable or deserialize
    private String name;

    @JsonIgnore//it means the field will be ignored for serializable or deserialize
    private String title;

    private int age;

    @JsonFormat(pattern="MM-dd-yyyy HH:mm:ss")//specify the date time format
    private Date updateTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)//embed customer logic/class for deserialize
    @JsonSerialize(using = LocalDateTimeSerializer.class)//embed customer logic/class for serializable
    private LocalDateTime updateTime2;//prefer to use LocalDateTime instead of Date

    @JsonInclude(JsonInclude.Include.NON_NULL)//this filed will NOT be included for serializable or deserialize if value is blank
    private String hobby;

    @JsonSerialize(using = GenderSerialize.class)
    @JsonDeserialize(using = GenderDeserialize.class)
    private int gender;

}
