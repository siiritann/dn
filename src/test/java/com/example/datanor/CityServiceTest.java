package com.example.datanor;

import com.example.datanor.exception.ApplicationException;
import com.example.datanor.exception.ObjectNotFoundException;
import com.example.datanor.model.City;
import com.example.datanor.repository.CityRepository;
import com.example.datanor.service.CityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.List;

import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CityServiceTest {


    @InjectMocks
    private CityService cityService;

    @Mock
    private CityRepository cityRepository;


    @Test
    public void getCityByName() {
        List<City> citiesList = new ArrayList<>();
        City city1 = new City(123, "Türi", "EE", "");
        City city2 = new City(890, "Türi vald", "EE", "");
        citiesList.add(city1);
        citiesList.add(city2);
        when(cityRepository.getCityByName("Türi")).thenReturn(citiesList);
        List<City> result = cityService.getCityByName("Türi");
        assertThat(result.size()).isEqualTo(2);
        verify(cityRepository, times(1)).getCityByName("Türi");
    }


    @Test
    public void addTrackedCity_WhenCityDoesntExist() {
        when(cityRepository.getMyCitiesIds()).thenReturn(of(123L, 321L));
        cityService.addTrackedCity(222);
        verify(cityRepository, times(1)).addTrackedCity(222);
    }

    @Test
    public void addTrackedCity_WhenCityAlreadyExists() {
        when(cityRepository.getMyCitiesIds()).thenReturn(of(123L, 321L));
        assertThrows(ApplicationException.class, () -> cityService.addTrackedCity(123));
    }


    @Test
    public void getMyCities() {
        when(cityRepository.getMyCities()).thenReturn(of(new City(123, "Tartu", "EE", "")));
        List<City> result = cityService.getMyCities();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getName()).isEqualTo("Tartu");
        verify(cityRepository, times(1)).getMyCities();
    }


    @Test
    public void getMyCitiesIds() {
        when(cityRepository.getMyCitiesIds()).thenReturn(of(123L, 321L));
        List<Long> result = cityService.getMyCitiesIds();
        assertThat(result.size()).isEqualTo(2);
        verify(cityRepository, times(1)).getMyCitiesIds();
    }


    @Test
    public void deleteCity_WhenCityExists() {
        when(cityRepository.deleteCity(123)).thenReturn(1);
        String result = cityService.deleteCity(123);
        assertThat(result).isEqualTo("City deleted");
        verify(cityRepository, times(1)).deleteCity(123);
    }

    @Test
    public void deleteCity_WhenCityDoesntExistPositiveValue() {
        when(cityRepository.deleteCity(123)).thenReturn(0); // pigem las olla, muidu default ise on 0 ja tekib võlts kindlustunne
        ApplicationException applicationException = assertThrows(ApplicationException.class, () -> cityService.deleteCity(123));
        assertThat(applicationException.getMessage()).isEqualTo("Something went wrong"); // võiks olla vea sisu kontroll
        verify(cityRepository, times(1)).deleteCity(123); // võib olla siin
    }

    @Test
    public void deleteCity_WhenCityDoesntExistNegativeValue() {
        assertThrows(ApplicationException.class, () -> cityService.deleteCity(-123));
    }


    @Test
    public void getCityNameById_WhenCorrectId() {
        when(cityRepository.getCityNameById(123)).thenReturn("Tartu");
        String result = cityService.getCityNameById(123);
        assertThat(result).isEqualTo("Tartu");
        verify(cityRepository, times(1)).getCityNameById(123);
    }

    @Test
    public void getCityNameById_WhenIncorrectId() {
        when(cityRepository.getCityNameById(123)).thenThrow(EmptyResultDataAccessException.class);
        // throw exception sest süsteemi mõttes on see exception mitte string
        assertThrows(ObjectNotFoundException.class, () -> cityService.getCityNameById(123));
    }

}
