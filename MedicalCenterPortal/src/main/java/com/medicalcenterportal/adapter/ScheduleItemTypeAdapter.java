

package com.medicalcenterportal.adapter;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.medicalcenter.entities.ScheduleItem;
import com.medicalcenter.enums.Period;


public class ScheduleItemTypeAdapter extends TypeAdapter<ScheduleItem> {

    @Override
    public void write(JsonWriter out, ScheduleItem value) throws IOException {
        out.beginObject(); // Bắt đầu viết đối tượng JSON

        // Ghi thông tin về bác sĩ
        out.name("doctor").value(value.getDoctor().toString());

        // Ghi thông tin về ngày làm việc
        out.name("workdates").value(value.getWorkdates().toString());

        // Ghi thông tin về phòng làm việc
        out.name("room").value(value.getRoom());

        // Ghi thông tin về khoảng thời gian hẹn
        out.name("appointmentPeriod").value(value.getAppointmentPeriod().toString());

        out.endObject(); // Kết thúc viết đối tượng JSON
    }

    @Override
    public ScheduleItem read(JsonReader in) throws IOException {
        // Không cần phương thức này trong trường hợp bạn chỉ muốn ghi dữ liệu JSON
        return null;
    }
}
