package com.sheli.learning.beans.factorybeans;

import com.sheli.learning.objects.Book;
import org.springframework.beans.factory.FactoryBean;


public class MyFactoryBean implements FactoryBean<Book> {
    //1.return an instance
    @Override
    public Book getObject() throws Exception {
        return new Book("2234R64", "A Book for Coders");
    }

    //2.return the type of the class
    @Override
    public Class<?> getObjectType() {
        return Book.class;
    }

    //3.Is singleton?
    @Override
    public boolean isSingleton() {
        return true;
    }
}
