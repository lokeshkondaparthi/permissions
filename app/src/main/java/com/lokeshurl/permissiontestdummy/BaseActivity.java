package com.lokeshurl.permissiontestdummy;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import java.util.Random;
public abstract class BaseActivity extends AppCompatActivity {
    public abstract void permissionGranted(String permission, int requestCode);
    public String manualPermissionDesc;
    public String permission;
    public int requestCode;
    //public abstract void onFragmentInteraction(String grantedPermissionDesc);

    /**
     * Request and check any permission, show explanation dialogue, state granted permissions.
     * @param permission run-time permission
     * @param description for explanation dialogue.
     */
    protected void requestPermission(String permission, int requestCode, String description, String manualPermissionDesc) {
        this.manualPermissionDesc = manualPermissionDesc;
        this.permission = permission;
        this.requestCode = requestCode;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            /* Read contacts*/
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                if( ActivityCompat.shouldShowRequestPermissionRationale(this,
                        permission)){
                    showPermissionDialog(permission, description);
                }else {
                    ActivityCompat.requestPermissions( this, new String[]{permission},
                            requestCode);
                }
            } else {
                permissionGranted(permission, this.requestCode);
            }
        } else {
            permissionGranted(permission, this.requestCode);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull final String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                permissionGranted(permissions[0], this.requestCode);
            else if (grantResults[0] == PackageManager.PERMISSION_DENIED &&
                    !ActivityCompat.shouldShowRequestPermissionRationale(this,
                            permissions[0])) {
                showPermissionDialog(null, manualPermissionDesc);
            }
        }
    }



    public void showPermissionDialog(final String permission, String description) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(description);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (permission == null) {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.setData(Uri.fromParts("package", getPackageName(), null));
                    // Same request code transferred to activity.
                    startActivityForResult(intent, requestCode);
                }else{
                    ActivityCompat.requestPermissions(BaseActivity.this, new String[]{permission},
                            requestCode);
                }
                dialog.dismiss();
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (this.requestCode == requestCode) {
          if(ContextCompat.checkSelfPermission(this, permission)
                  == PackageManager.PERMISSION_GRANTED)
            permissionGranted(permission, this.requestCode);
        }
    }
}
