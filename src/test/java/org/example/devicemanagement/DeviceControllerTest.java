package org.example.devicemanagement;

import org.example.devicemanagement.api.utils.DeviceDTO;
import org.example.devicemanagement.db.repository.DeviceRepository;
import org.example.devicemanagement.domain.model.Device;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class DeviceControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DeviceRepository deviceRepository;

    @Autowired
    private JacksonTester<Device> jsonDevice;

    @Autowired
    private JacksonTester<List<Device>> jsonDeviceList;

    @Autowired
    private JacksonTester<DeviceDTO> jsonDeviceDto;

    @Test
    public void canFetchDeviceById() throws Exception {
        // given
        given(deviceRepository.findById(2L)).willReturn(Optional.of(testDeviceApple));

        // when
        MockHttpServletResponse response = mvc.perform(
                        MockMvcRequestBuilders
                                .get("/devices/2")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonDevice.write(testDeviceApple).getJson()
        );
    }

    @Test
    public void returns404IfDeviceNotFound() throws Exception {
        // given
        given(deviceRepository.findById(2L)).willReturn(Optional.empty());

        // when
        MockHttpServletResponse response = mvc.perform(
                        MockMvcRequestBuilders
                                .get("/devices/2")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void canDeleteDeviceById() throws Exception {
        // given
        given(deviceRepository.findById(2L)).willReturn(Optional.of(testDeviceApple));

        // when
        MockHttpServletResponse response = mvc.perform(
                        MockMvcRequestBuilders
                                .delete("/devices/2")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void canFetchAllDevices() throws Exception {
        // given
        given(deviceRepository.findAll()).willReturn(Arrays.asList(testDeviceApple, testDeviceSamsung, testDeviceLG));

        // when
        MockHttpServletResponse response = mvc.perform(
                        MockMvcRequestBuilders
                                .get("/devices")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonDeviceList.write(fetchedDevices).getJson()
        );
    }

    @Test
    public void canFetchAllDevicesByBrand() throws Exception {
        // given
        given(deviceRepository.findByBrand("LG")).willReturn(Arrays.asList(testDeviceLG, testDeviceLGPro));

        // when
        MockHttpServletResponse response = mvc.perform(
                        MockMvcRequestBuilders
                                .get("/devices?brand=LG")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonDeviceList.write(fetchedDevicesByBrand).getJson()
        );
    }

    @Test
    public void canCreateNewDevice() throws Exception {
        // given
        given(deviceRepository.save(any())).willReturn(testDeviceApple);

        // when
        MockHttpServletResponse response = mvc.perform(
                        MockMvcRequestBuilders
                                .post("/devices")
                                .content(jsonDeviceDto.write(testDeviceDtoApple).getJson())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonDevice.write(testDeviceApple).getJson()
        );
    }

    @Test
    public void canPartiallyUpdateDevice() throws Exception {
        // given
        given(deviceRepository.save(any())).willReturn(testDeviceAppleUpdated);
        given(deviceRepository.findById(1L)).willReturn(Optional.of(testDeviceApple));

        // when
        MockHttpServletResponse response = mvc.perform(
                        MockMvcRequestBuilders
                                .put("/devices/1")
                                .content(jsonDeviceDto.write(testDeviceDtoApplePartialUpdate).getJson())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonDevice.write(testDeviceAppleUpdated).getJson()
        );
    }

    @Test
    public void canFullyUpdateDevice() throws Exception {
        // given
        given(deviceRepository.save(any())).willReturn(testDeviceLGPro);
        given(deviceRepository.findById(1L)).willReturn(Optional.of(testDeviceApple));

        // when
        MockHttpServletResponse response = mvc.perform(
                        MockMvcRequestBuilders
                                .put("/devices/1")
                                .content(jsonDeviceDto.write(testDeviceDtoAppleFullUpdate).getJson())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonDevice.write(testDeviceLGPro).getJson()
        );
    }

    private final Device testDeviceApple = new Device("Iphone", "Apple");
    private final Device testDeviceAppleUpdated = new Device("Iphone Pro", "Apple");
    private final Device testDeviceSamsung = new Device("Galaxy", "Samsung");
    private final Device testDeviceLG = new Device("Nexus", "LG");
    private final Device testDeviceLGPro = new Device("Pro Mate", "LG");

    private final DeviceDTO testDeviceDtoApple = new DeviceDTO("Iphone", "Apple");
    private final DeviceDTO testDeviceDtoApplePartialUpdate = new DeviceDTO("Iphone Pro", null);
    private final DeviceDTO testDeviceDtoAppleFullUpdate = new DeviceDTO("Pro Mate", "LG");

    private final List<Device> fetchedDevices = Arrays.asList(testDeviceApple, testDeviceSamsung, testDeviceLG);
    private final List<Device> fetchedDevicesByBrand = Arrays.asList(testDeviceLG, testDeviceLGPro);


}
