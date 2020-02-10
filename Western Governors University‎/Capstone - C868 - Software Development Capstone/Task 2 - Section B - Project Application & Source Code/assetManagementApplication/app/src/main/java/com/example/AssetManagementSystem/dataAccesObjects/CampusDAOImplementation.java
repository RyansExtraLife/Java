package com.example.AssetManagementSystem.dataAccesObjects;


import com.example.AssetManagementSystem.modules.Campus;

import java.util.ArrayList;

interface CampusDAOImplementation {

    boolean addCampus(Campus passedCampus);

    boolean removeCampus(Campus passedCampus);

    boolean updateCampus(Campus passedCampus);

    int getCampusCount();

    ArrayList<Campus> getCampuses();

    Campus getCampusById(int passedCampusId);
}
