package ru.p4f.soap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lavasoft.GeoIPService;
import model.GeoIP;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class GeoIpServiceTest {

    @Test
    public void testMyIp() throws JsonProcessingException {
        XmlMapper mapper = new XmlMapper();
        String xml = new GeoIPService().getGeoIPServiceSoap12().getIpLocation20("95.79.86.207");
        GeoIP geoIP = mapper.readValue(xml, GeoIP.class);
        assertEquals(geoIP.getCountry(),"RU");
    }
}
