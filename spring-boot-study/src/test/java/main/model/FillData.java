package main.model;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class FillData {
    private String name;
    private Double number;
    private Date date;
}
