/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import org.springframework.stereotype.Service;
import wad.domain.Message;

/**
 *
 * @author Asus
 */
@Service
public class RacistBot {
        private final String[] lentavatLauseet = {

        "Miksi neekereiden ruumisarkuissa on reikiä? - Jotta madot pääsisivät oksennukselle.",
        "Miksi neekereillä on niin paksu lompakko? - Heille maksetaan palkka banaaneina.",
        "Miksi neekereillä on suuret silmät? - Että näkisivät paremmin nälkää.",
        " Miksi neekereillä on vaaleat jalkapohjat? - Koripallokentälle ei saa mennä mustapohjaisilla jalkineilla.",
        "Miksi neekerilapset eivät tykkää leikkiä hiekkalaatikolla? - Sisäsiistit kissat yrittävät haudata heitä.",
        "Miksi neekeripoliiseilla on mustat pamput? - Iskut eivät näy kohteissa.",
        "Miksi neekerit kadehtivat kalalokkia? - Koska kalalokit ehtivät aina ensiksi roskalaatikolle.",
        "Mikä on mustavalkoinen ja pyörii ympäri torilla? - Somali ja lokki taistelevat pullanmurusta.",
        "Mikä on neekerien ehkäisyväline? - Clearasil, se ehkäisee mustapäitä syntymästä.",
        "Miksi neekerit pitävät elokuvissa käydessään valkoisia hansikkaita? - Että ne näkisivät, milloin lakritsa loppuu.",
        "Miksi neekereillä on niin litteä nenä? - Aina kun neekeri pyrkii ravintolaan, portsari ojentaa kätensä suoraksi ja sanoo: Sori, tänne ei mahdu.",
        "Neekeri oli odottamassa linja-autoa pysäkillä. Yhtäkkiä hänelle tuli kova paskahätä. No, koska ketään ei ollut näkyvillä hän päätti vääntää paskat siihen paikkaan. Hän saikin juuri paskottua ja vedettyä housut jalkaan kun linja-auto tuli. Neekeri nousi linja-autoon ja kysyi kuskilta, paljonko meno kaupunkiin maksaa. Kuski vastasi: - Kympin sinulta, ja vitosen tuolta pikkuiselta.",
        "Mikä neekeristä tulee kun se syö varoituskolmion ? - toblerone",
        "kumpi putoaa ensin lentokoneesta: somali vai neekeri? mitä sillä on väliä!!!",
        "Mitä saa kun ampuu neekerin? Stereonsa ja polkupyäränsä takas!",
        " Mitä pieni neekerilapsi luuli kun hänellä oli ripuli? Hän luuli, että hän sulaa.",
        "Mitä ajattelee alaston neekeri mennessään suojatien yli? He näkevät minut - he eivät näe minua - he näkevät minut...",
        "Mitä Jumala sanoi kun loi neekerit? Hemmetti ku palo pohjaan.",
        "Miksi Kanadassa ei ole neekereitä? Koska ne on kaikki puristettu jääkiekoiksi.",
        "Mitä eroa on neekerillä ja ilotulitusraketilla? Neekereitä saa ampuu ennen kahtatoista.",
        "Mitä eroa on neekerillä ja sipulilla? Neekeriä viipaloidessa ei tuu itku silmään."};
        
        private String name = "RasistiBot";

    public Message getMessage() {
        Message msg = new Message();
        msg.setContent(lentavatLauseet[new Random().nextInt(lentavatLauseet.length)]);
        msg.setUsername(name);
        String timeStamp = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
        msg.setTime(timeStamp);
        
        return msg;
    }
}
