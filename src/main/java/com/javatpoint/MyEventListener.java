package com.javatpoint;

import java.sql.SQLException;

public interface MyEventListener {
    void onMyEvent (TwitterEvent e) throws SQLException;
}
