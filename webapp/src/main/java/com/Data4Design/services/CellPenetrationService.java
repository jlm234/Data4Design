package com.Data4Design.services;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import com.Data4Design.results.LongResult;

public class CellPenetrationService implements ICellPenetrationService
{
    public LongResult getCellPenetration(String countryName)
    {
        LongResult cellpen = new LongResult();
        try
        {

            JSONParser parser = new JSONParser();
            URL jsonFile = new URL("https://raw.githubusercontent.com/iancoleman/cia_world_factbook_api/master/data/2018-02-26_factbook.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(jsonFile.openStream()));

            JSONObject totalObject = (JSONObject) parser.parse(reader);
            JSONObject countries = (JSONObject) totalObject.get("countries");
            JSONObject countryWanted = (JSONObject) countries.get(countryName);
            JSONObject dataObject = (JSONObject) countryWanted.get("data");
            JSONObject communications = (JSONObject) dataObject.get("communications");
            JSONObject telephoneObject = (JSONObject) communications.get("telephones");
            JSONObject mobile = (JSONObject) telephoneObject.get("mobile_cellular");
            //cellPenetration = (LongResult) mobile.get("total");
            cellPenetration = -1;
            reader.close();
        }

        catch(MalformedURLException e)
        {
            System.out.println("Bad URL");
        }

        catch(IOException e)
        {
            System.out.println("Need to update factbook date.");

        }
        catch(ParseException e)
        {
            System.out.println("Trouble parsing data.");
        }
        catch(NullPointerException e)
        {
            System.out.println("Data not available.");
            cellPenetration = -1;
        }

        cellpen.setData(cellPenetration);
        return cellpen;
    }
    private long cellPenetration;
}
