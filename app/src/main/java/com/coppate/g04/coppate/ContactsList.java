package com.coppate.g04.coppate;

/**
 * Created by Jul-note on 14/10/2016.
 */

import java.util.ArrayList;

public class ContactsList {

    public ArrayList<Contact> contactArrayList;

    ContactsList() {

        contactArrayList = new ArrayList<Contact>();
    }

    public int getCount() {

        return contactArrayList.size();
    }

    public void addContact(Contact contact) {
        contactArrayList.add(contact);
    }

    public void removeContact(Contact contact) {
        contactArrayList.remove(contact);
    }

    public Contact getContact(int id) {

        for (int i = 0; i < this.getCount(); i++) {
            if (Integer.parseInt(contactArrayList.get(i).id) == id)
                return contactArrayList.get(i);
        }

        return null;
    }
}
