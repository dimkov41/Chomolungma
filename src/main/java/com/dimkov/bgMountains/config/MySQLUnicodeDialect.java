package com.dimkov.bgMountains.config;

import org.hibernate.dialect.MariaDBDialect;


public class MySQLUnicodeDialect extends MariaDBDialect {
    @Override
    public String getTableTypeString() {
        return "ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci";
    }
}
