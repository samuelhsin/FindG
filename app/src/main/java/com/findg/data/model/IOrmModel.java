package com.findg.data.model;

import java.io.Serializable;

public interface IOrmModel extends Serializable {
    long getId();

    void setId(long id);

    String getName();

    void setName(String name);
}
