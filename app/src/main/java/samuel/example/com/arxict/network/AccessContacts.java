package samuel.example.com.arxict.network;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import samuel.example.com.arxict.model.ContactData;

/**
 * Created by samuel on 7/5/2017.
 */

public class AccessContacts {


    private ContentResolver contentResolver ;
    private Cursor cursor;
    private int counter;

    public  AccessContacts (ContentResolver contentResolver)
    {
        this.contentResolver =contentResolver ;
    }

    /**
     * getContacts used to get user Contacts
     * @return List<ContactData>
     */

    public List<ContactData> getContacts(  ) {
        List<ContactData> contactDataList = new ArrayList<>();
        String phoneNumber = null;
        String email = null;
        Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
        String _ID = ContactsContract.Contacts._ID;
        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
        String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
        Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
        Uri EmailCONTENT_URI =  ContactsContract.CommonDataKinds.Email.CONTENT_URI;
        String EmailCONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
        String DATA = ContactsContract.CommonDataKinds.Email.DATA;
        cursor = contentResolver.query(CONTENT_URI, null,null, null, null);


        // Iterate every contact in the phone
        if (cursor.getCount() > 0) {
            counter = 0;

            while (cursor.moveToNext()) {
                counter++;
                if(counter >200)
                   break;
                ContactData contactData = new ContactData();
                String contact_id = cursor.getString(cursor.getColumnIndex( _ID ));
                contactData.setPhoto( retrieveContactPhoto (contact_id) );

                String name = cursor.getString(cursor.getColumnIndex( DISPLAY_NAME ));
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex( HAS_PHONE_NUMBER )));
                if (hasPhoneNumber > 0) {

                     contactData.setName(name);
                    //This is to read multiple phone numbers associated with the same contact
                    Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[] { contact_id }, null);
                    while (phoneCursor.moveToNext()) {
                        phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                        break;
                    }
                    phoneCursor.close();
                    contactData.setPhone(phoneNumber);

                    // Read every email id associated with the contact

                    boolean emailExist=false;
                    Cursor emailCursor = contentResolver.query(EmailCONTENT_URI,    null, EmailCONTACT_ID+ " = ?", new String[] { contact_id }, null);
                    while (emailCursor.moveToNext()) {
                        email = emailCursor.getString(emailCursor.getColumnIndex(DATA));
                        emailExist=true;
                        contactData.setEmail(email) ;

                        break;
                    }
                    if (!emailExist)
                        contactData.setEmail(null) ;
                    emailCursor.close();

                }
                // check if the contact empty
                if(name!=null || phoneNumber!= null )
                {
                    contactDataList.add(contactData);
                }

            }
        }

        return contactDataList ;
    }

    /**
     * this function used to get user image
     * @param contactID
     * @return user image Bitmap
     */
    private Bitmap retrieveContactPhoto(String contactID ) {

        Bitmap photo = null;

        try {

            InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(contentResolver,
                    ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(contactID)));



            if (inputStream != null) {
                photo = BitmapFactory.decodeStream(inputStream);
                inputStream.close();
                return photo ;
            }

            else
                return null;


        } catch (IOException e) {
            e.printStackTrace();
            return null;

        }

    }
}