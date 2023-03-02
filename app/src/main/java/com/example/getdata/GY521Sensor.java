package com.example.getdata;

import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
public class GY521Sensor {
    private static final int ADDRESS = 0x68;
    private static final int REG_ACCEL_X_HIGH = 0x3B;
    private static final int REG_ACCEL_Y_HIGH = 0x3D;
    private static final int REG_ACCEL_Z_HIGH = 0x3F;

    private I2CBus bus;
    private I2CDevice device;

    public GY521Sensor() throws Exception {
        bus = I2CFactory.getInstance(I2CBus.BUS_1);
        device = bus.getDevice(ADDRESS);

        device.write(0x6B, (byte) 0x00); // Wake up the device
        device.write(0x1C, (byte) 0x00); // Set accelerometer range to +/- 2g
    }

    public double[] readData() throws Exception {
        byte[] data = new byte[6];
        device.read(REG_ACCEL_X_HIGH, data, 0, 6);

        int x = (data[0] << 8) | (data[1] & 0xFF);
        int y = (data[2] << 8) | (data[3] & 0xFF);
        int z = (data[4] << 8) | (data[5] & 0xFF);

        double ax = x / 16384.0;
        double ay = y / 16384.0;
        double az = z / 16384.0;

        return new double[] {ax, ay, az};
    }
}
