package org.uresti.pozarreal.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.uresti.pozarreal.dto.StreetInfo;
import org.uresti.pozarreal.model.Street;
import org.uresti.pozarreal.repository.HousesRepository;
import org.uresti.pozarreal.repository.RepresentativeRepository;
import org.uresti.pozarreal.repository.StreetRepository;
import org.uresti.pozarreal.model.House;
import org.uresti.pozarreal.model.Representative;

import java.util.*;

public class StreetServiceImplTests {

    @Test
    public void givenAnEmptyStreetList_whenGetStreets_thenEmptyListIsReturned(){
        // Given:
        StreetRepository streetRepository = Mockito.mock(StreetRepository.class);
        RepresentativeRepository representativeRepository = null;
        HousesRepository housesRepository = null;
        StreetServiceImpl streetService = new StreetServiceImpl(streetRepository, representativeRepository, housesRepository);

        List<Street> lista = new LinkedList<>();

        Mockito.when(streetRepository.findAll()).thenReturn(lista);

        // When:
        List<Street> streets = streetService.getStreets();

        // Then:
        Assertions.assertTrue(streets.isEmpty());
    }

    @Test
    public void givenAnStreetListWithTwoElements_whenGetStreets_thenListWithTwoElementsIsReturned(){
        // Given:
        StreetRepository streetRepository = Mockito.mock(StreetRepository.class);
        RepresentativeRepository representativeRepository = null;
        HousesRepository housesRepository = null;
        StreetServiceImpl streetService = new StreetServiceImpl(streetRepository, representativeRepository, housesRepository);

        List<Street> lista = new LinkedList<>();

        Street street1 = new Street();
        street1.setId("id1");
        street1.setName("Street 1");
        lista.add(street1);

        Street street2 = new Street();
        street2.setId("id2");
        street2.setName("Street 2");
        lista.add(street2);

        Mockito.when(streetRepository.findAll()).thenReturn(lista);


        // When:
        List<Street> streets = streetService.getStreets();

        // Then:
        Assertions.assertEquals(2, streets.size());
        Assertions.assertEquals("id1", streets.get(0).getId());
        Assertions.assertEquals("Street 1", streets.get(0).getName());
        Assertions.assertEquals("id2", streets.get(1).getId());
        Assertions.assertEquals("Street 2", streets.get(1).getName());
    }

    @Test
    void StreetInfoTest(){
        //Given
        StreetRepository streetRepository = Mockito.mock(StreetRepository.class);
        HousesRepository housesRepository = Mockito.mock(HousesRepository.class);
        RepresentativeRepository representativeRepository = Mockito.mock(RepresentativeRepository.class);
        StreetServiceImpl streetService = new StreetServiceImpl(streetRepository, representativeRepository, housesRepository);

        Street street = new Street();
        Representative representative = new Representative();
        List <House> list = new LinkedList<>();

        String streetId = "1";
        String streetName = "street";

        street.setId(streetId);
        street.setName(streetName);

        representative.setName("Nagito");
        representative.setId("X69X");
        representative.setStreet("strt1");
        representative.setAddress("x location");
        representative.setPhone("444555999");

        House house1 = new House();
        house1.setStreet("street");
        house1.setId("1");
        house1.setNumber("1");
        list.add(house1);

        Mockito.when(streetRepository.findById(streetId)).thenReturn(Optional.of(street));
        Mockito.when(representativeRepository.findRepresentativeByStreet(streetId)).thenReturn(representative);
        Mockito.when(housesRepository.findAllByStreet(streetId)).thenReturn(list);

        //When
        StreetInfo streetInfo = streetService.getStreetInfo(streetId);

        //Then
        Assertions.assertEquals(streetId,streetInfo.getId());
        Assertions.assertEquals(streetName,streetInfo.getName());

        Assertions.assertEquals("Nagito",streetInfo.getRepresentative().getName());
        Assertions.assertEquals("X69X",streetInfo.getRepresentative().getId());
        Assertions.assertEquals("strt1",streetInfo.getRepresentative().getStreet());
        Assertions.assertEquals("x location",streetInfo.getRepresentative().getAddress());
        Assertions.assertEquals("444555999",streetInfo.getRepresentative().getPhone());

        Assertions.assertEquals(1,streetInfo.getHouses().size());

        Assertions.assertEquals("street",streetInfo.getHouses().get(0).getStreet());
        Assertions.assertEquals("1",streetInfo.getHouses().get(0).getId());
        Assertions.assertEquals("1",streetInfo.getHouses().get(0).getNumber());
    }



}
