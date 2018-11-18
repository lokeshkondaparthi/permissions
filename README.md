# Run time permissions

<b>Code also availabe here :</b><br/> 
https://sites.google.com/site/lokeshurl/smart-developers/runtime-permissions

<b> How to use it </b>
Extend your activity with BaseActivity(.java) so that you can call methods of BaseActivity from your Activity.

for example if you want to get/retrieve contacts from device, you definately check permission of contact(run time) then if the permission is already granted/ run time granted then you supposed to go write logic to get contacts. Simply we can say you ask permission then write the code for get contats.

<h3> Permission granted ---> write the code for get contacts. </h3><br/>
<h4> <b>public void permissionGranted(String permission){...} </b> </h4> <br/>

The above method is executed when permission granted at any time. So you can write the code of Contacts code inside it.
 <b>For ex:<b>
 <p>
  
      @Override
    public void permissionGranted(String permission) {
        if (permission.equals(Manifest.permission.READ_CONTACTS))
            Toast.makeText(this, "Contact read permission granted", Toast.LENGTH_SHORT).show();
            // TO-DO here you can write the code/logic to get contacts. 
    }

  </p>

  <p>
  But, you will have simple question, how does this(permissionGranted()) method is called?<br/>
 Where you want to write the code for contacts, there you can ask permission. Becacuse user will see the permission as soon as they may accept permission, then permissionGranted() method will be executed everytime.
 </p>

  <h3> Request permission ---> instead of directly wirte the code for get contacts.  </h3><br/>
  <p>
  
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_contact:
                requestPermission(Manifest.permission.READ_CONTACTS,
                        "Please accept contact permission, app uses it. It's secure",
                        "You denied permission of contact, please accept in permissions settings page");
                break;

        }
    }
  </p>
  
  <h1> Code </h1> <br/>
  <p>
  public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Button mBTContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBTContact = findViewById(R.id.bt_contact);

        mBTContact.setOnClickListener(this);
    }

    @Override
    public void permissionGranted(String permission) {
        if (permission.equals(Manifest.permission.READ_CONTACTS))
            Toast.makeText(this, "Contact read permission granted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_contact:
                requestPermission(Manifest.permission.READ_CONTACTS,
                        "Please accept now contact permission etc",
                        "You denied permission of contact, please accept in permissions settings page");
                break;

        }
    }
}

  
  </p>
    
    
 


