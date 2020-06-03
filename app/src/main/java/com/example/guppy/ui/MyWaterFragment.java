package com.example.guppy.ui;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.guppy.R;
import com.example.guppy.databinding.FragmentMyWaterBinding;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class MyWaterFragment extends Fragment {
    private FragmentMyWaterBinding binding;
    private final static UUID BT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice mBluetoothDevice;
    private BluetoothSocket mBluetoothSocket;
    private Set<BluetoothDevice> mPairedDevices;
    private List<String> mListPairedDevices;
    private InputStream InStream;
    private OutputStream OutStream;
    private Thread workerThread = null;
    private byte[] readBuffer;
    private int readBufferPosition;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_water, container, false);
        View root = binding.getRoot();

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        binding.btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mBluetoothAdapter.isEnabled()){
                    mPairedDevices = mBluetoothAdapter.getBondedDevices();

                    if(mPairedDevices.size() > 0){
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("\"MyGuppy\" 선택!");

                        mListPairedDevices = new ArrayList<>();
                        for(BluetoothDevice device : mPairedDevices){
                            mListPairedDevices.add(device.getName());
                        }

                        final CharSequence[] items = mListPairedDevices.toArray(new CharSequence[mListPairedDevices.size()]);
                        mListPairedDevices.toArray(new CharSequence[mListPairedDevices.size()]);

                        builder.setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                for(BluetoothDevice tempDevice : mPairedDevices){
                                    if(items[which].toString().equals(tempDevice.getName())){
                                        mBluetoothDevice = tempDevice;
                                        break;
                                    }
                                }
                                try{
                                    mBluetoothSocket = mBluetoothDevice.createRfcommSocketToServiceRecord(BT_UUID);
                                    mBluetoothSocket.connect();
                                    if(mBluetoothSocket.isConnected()){
                                        Toast.makeText(getContext(), "연결되었습니다.", Toast.LENGTH_LONG).show();
                                        binding.btnConnect.setVisibility(View.INVISIBLE);
                                    }
                                    OutStream = mBluetoothSocket.getOutputStream();
                                    InStream = mBluetoothSocket.getInputStream();
                                    receiveData();
                                } catch (IOException e) {
                                    Toast.makeText(getContext(), "블루투스 연결 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
                                }

                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                    else{
                        Toast.makeText(getContext(), "페어링된 장치가 없습니다.", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(getContext(),"휴대전화의 블루투스를 켜주세요.", Toast.LENGTH_SHORT).show();
                }
            }

            private void receiveData() {
                final Handler handler = new Handler();

                readBufferPosition = 0;
                readBuffer = new byte[1024];

                workerThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(!Thread.currentThread().isInterrupted()) {
                            try {
                                int byteAvailable = InStream.available();
                                if(byteAvailable > 0) {
                                    byte[] bytes = new byte[byteAvailable];
                                    InStream.read(bytes);

                                    for(int i = 0; i < byteAvailable; i++) {
                                        byte tempByte = bytes[i];
                                        if(tempByte == '\n') {

                                            final String data = new String(readBuffer);
                                            readBufferPosition = 0;
                                            handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    String[] array = data.split(",");
                                                    binding.valDegree.setText(array[0].concat("℃"));
                                                    binding.valPh.setText(array[1]);
                                                    binding.valOxygen.setText(array[2].concat("ppm"));
                                                    binding.valWaterLevel.setText(array[3].concat("cm"));
                                                    binding.valTurbidity.setText(array[4].concat("NTU"));
                                                }
                                            });

                                        }
                                        else {
                                            readBuffer[readBufferPosition++] = tempByte;
                                        }
                                    }
                                }
                            } catch (IOException e) {
                                break;
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                break;
                            }
                        }
                    }
                });
                workerThread.start();
            }
        });




        return root;
    }
}