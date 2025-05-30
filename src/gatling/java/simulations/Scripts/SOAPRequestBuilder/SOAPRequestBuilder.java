package simulations.Scripts.SOAPRequestBuilder;

import simulations.Scripts.Utilities.Feeders;
import simulations.Scripts.Utilities.NumberGenerator;
import simulations.Scripts.Utilities.RandomStringGenerator;
import simulations.Scripts.Utilities.TimestampGenerator;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import io.gatling.javaapi.core.Session;

public class SOAPRequestBuilder {

    public static String addDocumentDailyListUserRequest(Session session, String userName, String password) {
        // Retrieve values from session or define defaults if needed
        String courtHouseType = session.get("display_name") != null
        ? session.get("display_name").toString()
        : "";
    
        String courtHouseName = session.get("courthouse_name") != null
        ? session.get("courthouse_name").toString()
        : "";
    
        String courtHouseCode = session.get("courthouse_code") != null
        ? session.get("courthouse_code").toString()
        : "";
    
        String eventType = Feeders.getRandomEventCode(); 

        // Generate dynamic values
        NumberGenerator generator = new NumberGenerator(10);
        RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
        String documentName = randomStringGenerator.generateRandomString(10);
        String getCurrentTimestamp = TimestampGenerator.getCurrentTimestamp();

        // Construct SOAP request
        return String.format("<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" 
                + "  <s:Header>\n" 
                + "    <ServiceContext token=\"temporary/127.0.0.1-1694086218480-789961425\" xmlns=\"http://context.core.datamodel.fs.documentum.emc.com/\">\n" 
                + "      <Identities xsi:type=\"RepositoryIdentity\" userName=\"" + userName + "\" password=\"" + password + "\" repositoryName=\"moj_darts\" domain=\"\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>\n" 
                + "      <RuntimeProperties/>\n" 
                + "    </ServiceContext>\n" 
                + "  </s:Header>\n" 
                + "  <s:Body>\n" 
                + "    <ns5:addDocument xmlns:ns5=\"http://com.synapps.mojdarts.service.com\">\n" 
                + "      <messageId>18418</messageId>\n" 
                + "      <type>%s</type>\n" 
                + "      <subType>" + eventType + "</subType>\n" 
                + "      <document><![CDATA[<cs:DailyList xmlns:cs=\"http://www.courtservice.gov.uk/schemas/courtservice\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.courtservice.gov.uk/schemas/courtservice DailyList-v5-2.xsd\" xmlns:apd=\"http://www.govtalk.gov.uk/people/AddressAndPersonalDetails\"><cs:DocumentID><cs:DocumentName>Perf_Doc_" + documentName + "</cs:DocumentName><cs:UniqueID>Perf_" + generator.generateNextNumber() + "</cs:UniqueID><cs:DocumentType>" + eventType + "</cs:DocumentType><cs:TimeStamp>" + getCurrentTimestamp + "</cs:TimeStamp><cs:Version>1.0</cs:Version><cs:SecurityClassification>NPM</cs:SecurityClassification><cs:SellByDate>2010-12-15</cs:SellByDate><cs:XSLstylesheetURL>http://www.courtservice.gov.uk/transforms/courtservice/dailyListHtml.xsl</cs:XSLstylesheetURL></cs:DocumentID><cs:ListHeader><cs:ListCategory>Criminal</cs:ListCategory><cs:StartDate>2010-02-18</cs:StartDate><cs:EndDate>2010-02-18</cs:EndDate><cs:Version>FINAL v1</cs:Version><cs:CRESTprintRef>MCD/112585</cs:CRESTprintRef><cs:PublishedTime>2010-02-17T16:16:50</cs:PublishedTime><cs:CRESTlistID>12298</cs:CRESTlistID></cs:ListHeader><cs:CrownCourt><cs:CourtHouseType>" + courtHouseType + "</cs:CourtHouseType><cs:CourtHouseCode CourtHouseShortName=\"SNARE\">" + courtHouseCode + "</cs:CourtHouseCode><cs:CourtHouseName>" + courtHouseName + "</cs:CourtHouseName><cs:CourtHouseAddress><apd:Line>THE CROWN COURT AT " + courtHouseName + "</apd:Line><apd:Line>75 HOLLYBUSH HILL</apd:Line><apd:Line>" + courtHouseName + ", LONDON</apd:Line><apd:PostCode>E11 1QW</apd:PostCode></cs:CourtHouseAddress><cs:CourtHouseDX>DX 98240 WANSTEAD 2</cs:CourtHouseDX><cs:CourtHouseTelephone>02085300000</cs:CourtHouseTelephone><cs:CourtHouseFax>02085300072</cs:CourtHouseFax></cs:CrownCourt><cs:CourtLists><cs:CourtList><cs:CourtHouse><cs:CourtHouseType>" + courtHouseType + "</cs:CourtHouseType><cs:CourtHouseCode>" + courtHouseCode + "</cs:CourtHouseCode><cs:CourtHouseName>" + courtHouseName + "</cs:CourtHouseName></cs:CourtHouse><cs:Sittings><cs:Sitting><cs:CourtRoomNumber>1AB</cs:CourtRoomNumber><cs:SittingSequenceNo>1</cs:SittingSequenceNo><cs:SittingAt>10:00:00</cs:SittingAt><cs:SittingPriority>T</cs:SittingPriority><cs:Judiciary><cs:Judge><apd:CitizenNameSurname>N&#47;A</apd:CitizenNameSurname><apd:CitizenNameRequestedName>N&#47;A</apd:CitizenNameRequestedName><cs:CRESTjudgeID>0</cs:CRESTjudgeID></cs:Judge></cs:Judiciary><cs:Hearings><cs:Hearing><cs:HearingSequenceNumber>1</cs:HearingSequenceNumber><cs:HearingDetails HearingType=\"TRL\"><cs:HearingDescription>For Trial</cs:HearingDescription><cs:HearingDate>2010-02-18</cs:HearingDate></cs:HearingDetails><cs:CRESThearingID>1</cs:CRESThearingID><cs:TimeMarkingNote>10:00 AM</cs:TimeMarkingNote><cs:CaseNumber>T20107001</cs:CaseNumber><cs:Prosecution ProsecutingAuthority=\"Crown Prosecution Service\"><cs:ProsecutingReference>CPS</cs:ProsecutingReference><cs:ProsecutingOrganisation><cs:OrganisationName>Crown Prosecution Service</cs:OrganisationName></cs:ProsecutingOrganisation></cs:Prosecution><cs:CommittingCourt><cs:CourtHouseType>" + courtHouseType + "</cs:CourtHouseType><cs:CourtHouseCode CourtHouseShortName=\"BAM\">2725</cs:CourtHouseCode><cs:CourtHouseName>" + courtHouseName + "</cs:CourtHouseName><cs:CourtHouseAddress><apd:Line>7C HIGH STREET</apd:Line><apd:Line>-</apd:Line><apd:Line>BARNET</apd:Line><apd:PostCode>EN5 5UE</apd:PostCode></cs:CourtHouseAddress><cs:CourtHouseDX>DX 8626 BARNET</cs:CourtHouseDX><cs:CourtHouseTelephone>02084419042</cs:CourtHouseTelephone></cs:CommittingCourt><cs:Defendants><cs:Defendant><cs:PersonalDetails><cs:Name><apd:CitizenNameForename>Franz</apd:CitizenNameForename><apd:CitizenNameSurname>KAFKA</apd:CitizenNameSurname></cs:Name><cs:IsMasked>no</cs:IsMasked><cs:DateOfBirth><apd:BirthDate>1962-06-12</apd:BirthDate><apd:VerifiedBy>not verified</apd:VerifiedBy></cs:DateOfBirth><cs:Sex>male</cs:Sex><cs:Address><apd:Line>ADDRESS LINE 1</apd:Line><apd:Line>ADDRESS LINE 2</apd:Line><apd:Line>ADDRESS LINE 3</apd:Line><apd:Line>ADDRESS LINE 4</apd:Line><apd:Line>SOMETOWN, SOMECOUNTY</apd:Line><apd:PostCode>GU12 7RT</apd:PostCode></cs:Address></cs:PersonalDetails><cs:ASNs><cs:ASN>0723XH1000000262665K</cs:ASN></cs:ASNs><cs:CRESTdefendantID>29161</cs:CRESTdefendantID><cs:PNCnumber>20123456789L</cs:PNCnumber><cs:URN>62AA1010646</cs:URN><cs:CustodyStatus>In custody</cs:CustodyStatus></cs:Defendant></cs:Defendants></cs:Hearing></cs:Hearings></cs:Sitting></cs:Sittings></cs:CourtList></cs:CourtLists></cs:DailyList>]]></document>\n" 
                + "    </ns5:addDocument>\n" 
                + "  </s:Body>\n" 
                + "</s:Envelope>",
                eventType);
    }

    public static String addDocumentEventUserRequest(Session session, String userName, String password) {
        // Retrieve values from session or define defaults if needed
        String courtHouseName = session.get("courthouse_name") != null
            ? session.get("courthouse_name").toString()
            : "";
        String courtRoom = session.get("courtroom_name") != null
            ? session.get("courtroom_name").toString()
            : "";

        // Generate dynamic values
        RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
        String caseName = randomStringGenerator.generateRandomString(10);
        String eventText = randomStringGenerator.generateRandomString(10);

        // Construct SOAP request
        return String.format("<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\">\n"
        + "  <s:Header>\n"
        + "    <ServiceContext token=\"temporary/127.0.0.1-1694086218480-789961425\" xmlns=\"http://context.core.datamodel.fs.documentum.emc.com/\">\n"
        + "      <Identities xsi:type=\"RepositoryIdentity\" userName=\"" + userName + "\" password=\"" + password + "\" repositoryName=\"moj_darts\" domain=\"\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>\n"
        + "      <RuntimeProperties/>\n"
        + "    </ServiceContext>\n"
        + "  </s:Header>\n"
        + "       <s:Body>\n"
        + "      <ns5:addDocument xmlns:ns5=\"http://com.synapps.mojdarts.service.com\">\n"
        + "            <messageId>18418</messageId>\n"
        + "            <type>1100</type>\n"
        + "            <subType>0</subType>\n"
        + "            <document>&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot; standalone=&quot;yes&quot;?&gt;&lt;be:DartsEvent\n"
        + "                    Y=&quot;2023&quot; S=&quot;44&quot; M=&quot;5&quot; MIN=&quot;53&quot; ID=&quot;-15&quot; H=&quot;15&quot;\n"
        + "                    D=&quot;5&quot; xmlns:be=&quot;urn:integration-cjsonline-gov-uk:pilot:entities&quot;&gt;&lt;be:CaseNumbers&gt;&lt;be:CaseNumber&gt;%s&lt;/be:CaseNumber&gt;&lt;/be:CaseNumbers&gt;&lt;be:CourtHouse&gt;%s&lt;/be:CourtHouse&gt;&lt;be:CourtRoom&gt;%s&lt;/be:CourtRoom&gt;&lt;be:%s&gt;Hearing\n"
        + "                    started&lt;/be:EventText&gt;&lt;/be:DartsEvent&gt;</document>\n"
        + "            </ns5:addDocument>\n"
        + "        </s:Body>\n"
        + "     </s:Envelope>",
        caseName, courtHouseName, courtRoom, eventText);
    }

    public static String addDocumentDailyListTokenRequest(Session session) {
        // Retrieve values from session or define defaults if needed
        String registrationToken = session.get("registrationToken") != null
            ? session.get("registrationToken").toString()
            : "";
        String courtHouseType = session.get("display_name") != null
            ? session.get("display_name").toString() 
            : "";
        String courtHouseName = session.get("courthouse_name") != null
            ? session.get("courthouse_name").toString()
            : "";
        String courtHouseCode = session.get("courthouse_code") != null
            ? session.get("courthouse_code").toString()
            : "";
        String eventType = Feeders.getRandomEventCode();
         
        // Generate dynamic values
        NumberGenerator generator = new NumberGenerator(10);
        RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
        String documentName = randomStringGenerator.generateRandomString(10);
        String getCurrentTimestamp = TimestampGenerator.getCurrentTimestamp();

        // Construct SOAP request
        return String.format("<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\">\n"
        + "   <s:Header>\n"
        + "      <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">\n"
        + "         <wsse:BinarySecurityToken QualificationValueType=\"http://schemas.emc.com/documentum#ResourceAccessToken\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" wsu:Id=\"RAD\">%s</wsse:BinarySecurityToken>\n"
        + "      </wsse:Security>\n"
        + "   </s:Header>\n"
        + "   <s:Body>\n"
        + "      <ns5:addDocument xmlns:ns5=\"http://com.synapps.mojdarts.service.com\">\n"
        + "         <messageId>18418</messageId>\n"
        + "         <type>%s</type>\n"
        + "         <subType>%s</subType>\n"
        + "         <document>&lt;cs:DailyList xmlns:cs=&quot;http://www.courtservice.gov.uk/schemas/courtservice&quot; xmlns:xsi=&quot;http://www.w3.org/2001/XMLSchema-instance&quot; xsi:schemaLocation=&quot;http://www.courtservice.gov.uk/schemas/courtservice DailyList-v5-2.xsd&quot; xmlns:apd=&quot;http://www.govtalk.gov.uk/people/AddressAndPersonalDetails&quot;&gt;&lt;cs:DocumentID&gt;&lt;cs:DocumentName&gt;Perf_Doc_" + documentName +"&lt;/cs:DocumentName&gt;&lt;cs:UniqueID&gt;Perf_" + generator.generateNextNumber() + "&lt;/cs:UniqueID&gt;&lt;cs:DocumentType&gt;" + eventType + "&lt;/cs:DocumentType&gt;&lt;cs:TimeStamp&gt;" + getCurrentTimestamp + "&lt;/cs:TimeStamp&gt;&lt;cs:Version&gt;1.0&lt;/cs:Version&gt;&lt;cs:SecurityClassification&gt;NPM&lt;/cs:SecurityClassification&gt;&lt;cs:SellByDate&gt;2010-12-15&lt;/cs:SellByDate&gt;&lt;cs:XSLstylesheetURL&gt;http://www.courtservice.gov.uk/transforms/courtservice/dailyListHtml.xsl&lt;/cs:XSLstylesheetURL&gt;&lt;/cs:DocumentID&gt;&lt;cs:ListHeader&gt;&lt;cs:ListCategory&gt;Criminal&lt;/cs:ListCategory&gt;&lt;cs:StartDate&gt;2010-02-18&lt;/cs:StartDate&gt;&lt;cs:EndDate&gt;2010-02-18&lt;/cs:EndDate&gt;&lt;cs:Version&gt;FINAL v1&lt;/cs:Version&gt;&lt;cs:CRESTprintRef&gt;MCD/112585&lt;/cs:CRESTprintRef&gt;&lt;cs:PublishedTime&gt;2010-02-17T16:16:50&lt;/cs:PublishedTime&gt;&lt;cs:CRESTlistID&gt;12298&lt;/cs:CRESTlistID&gt;&lt;/cs:ListHeader&gt;&lt;cs:CrownCourt&gt;&lt;cs:CourtHouseType&gt;%s&lt;/cs:CourtHouseType&gt;&lt;cs:CourtHouseCode CourtHouseShortName=&quot;SNARE&quot;&gt;%s&lt;/cs:CourtHouseCode&gt;&lt;cs:CourtHouseName&gt;%s&lt;/cs:CourtHouseName&gt;&lt;cs:CourtHouseAddress&gt;&lt;apd:Line&gt;THE CROWN COURT AT Bristol&lt;/apd:Line&gt;&lt;apd:Line&gt;75 HOLLYBUSH HILL&lt;/apd:Line&gt;&lt;apd:Line&gt;Bristol, LONDON&lt;/apd:Line&gt;&lt;apd:PostCode&gt;E11 1QW&lt;/apd:PostCode&gt;&lt;/cs:CourtHouseAddress&gt;&lt;cs:CourtHouseDX&gt;DX 98240 WANSTEAD 2&lt;/cs:CourtHouseDX&gt;&lt;cs:CourtHouseTelephone&gt;02085300000&lt;/cs:CourtHouseTelephone&gt;&lt;cs:CourtHouseFax&gt;02085300072&lt;/cs:CourtHouseFax&gt;&lt;/cs:CrownCourt&gt;&lt;cs:CourtLists&gt;&lt;cs:CourtList&gt;&lt;cs:CourtHouse&gt;&lt;cs:CourtHouseType&gt;%s&lt;/cs:CourtHouseType&gt;&lt;cs:CourtHouseCode&gt;%s&lt;/cs:CourtHouseCode&gt;&lt;cs:CourtHouseName&gt;%s&lt;/cs:CourtHouseName&gt;&lt;/cs:CourtHouse&gt;&lt;cs:Sittings&gt;&lt;cs:Sitting&gt;&lt;cs:CourtRoomNumber&gt;1&lt;/cs:CourtRoomNumber&gt;&lt;cs:SittingSequenceNo&gt;1&lt;/cs:SittingSequenceNo&gt;&lt;cs:SittingAt&gt;10:00:00&lt;/cs:SittingAt&gt;&lt;cs:SittingPriority&gt;T&lt;/cs:SittingPriority&gt;&lt;cs:Judiciary&gt;&lt;cs:Judge&gt;&lt;apd:CitizenNameSurname&gt;N/A&lt;/apd:CitizenNameSurname&gt;&lt;apd:CitizenNameRequestedName&gt;N/A&lt;/apd:CitizenNameRequestedName&gt;&lt;cs:CRESTjudgeID&gt;0&lt;/cs:CRESTjudgeID&gt;&lt;/cs:Judge&gt;&lt;/cs:Judiciary&gt;&lt;cs:Hearings&gt;&lt;cs:Hearing&gt;&lt;cs:HearingSequenceNumber&gt;1&lt;/cs:HearingSequenceNumber&gt;&lt;cs:HearingDetails HearingType=&quot;TRL&quot;&gt;&lt;cs:HearingDescription&gt;For Trial&lt;/cs:HearingDescription&gt;&lt;cs:HearingDate&gt;2010-02-18&lt;/cs:HearingDate&gt;&lt;/cs:HearingDetails&gt;&lt;cs:CRESThearingID&gt;1&lt;/cs:CRESThearingID&gt;&lt;cs:TimeMarkingNote&gt;10:00 AM&lt;/cs:TimeMarkingNote&gt;&lt;cs:CaseNumber&gt;T20107001&lt;/cs:CaseNumber&gt;&lt;cs:Prosecution ProsecutingAuthority=&quot;Crown Prosecution Service&quot;&gt;&lt;cs:ProsecutingReference&gt;CPS&lt;/cs:ProsecutingReference&gt;&lt;cs:ProsecutingOrganisation&gt;&lt;cs:OrganisationName&gt;Crown Prosecution Service&lt;/cs:OrganisationName&gt;&lt;/cs:ProsecutingOrganisation&gt;&lt;/cs:Prosecution&gt;&lt;cs:CommittingCourt&gt;&lt;cs:CourtHouseType&gt;%s&lt;/cs:CourtHouseType&gt;&lt;cs:CourtHouseCode CourtHouseShortName=&quot;BAM&quot;&gt;%s&lt;/cs:CourtHouseCode&gt;&lt;cs:CourtHouseName&gt;%s&lt;/cs:CourtHouseName&gt;&lt;cs:CourtHouseAddress&gt;&lt;apd:Line&gt;7C HIGH STREET&lt;/apd:Line&gt;&lt;apd:Line&gt;-&lt;/apd:Line&gt;&lt;apd:Line&gt;BARNET&lt;/apd:Line&gt;&lt;apd:PostCode&gt;EN5 5UE&lt;/apd:PostCode&gt;&lt;/cs:CourtHouseAddress&gt;&lt;cs:CourtHouseDX&gt;DX 8626 BARNET&lt;/cs:CourtHouseDX&gt;&lt;cs:CourtHouseTelephone&gt;02084419042&lt;/cs:CourtHouseTelephone&gt;&lt;/cs:CommittingCourt&gt;&lt;cs:Defendants&gt;&lt;cs:Defendant&gt;&lt;cs:PersonalDetails&gt;&lt;cs:Name&gt;&lt;apd:CitizenNameForename&gt;Franz&lt;/apd:CitizenNameForename&gt;&lt;apd:CitizenNameSurname&gt;KAFKA&lt;/apd:CitizenNameSurname&gt;&lt;/cs:Name&gt;&lt;cs:IsMasked&gt;no&lt;/cs:IsMasked&gt;&lt;cs:DateOfBirth&gt;&lt;apd:BirthDate&gt;1962-06-12&lt;/apd:BirthDate&gt;&lt;apd:VerifiedBy&gt;not verified&lt;/apd:VerifiedBy&gt;&lt;/cs:DateOfBirth&gt;&lt;cs:Sex&gt;male&lt;/cs:Sex&gt;&lt;cs:Address&gt;&lt;apd:Line&gt;ADDRESS LINE 1&lt;/apd:Line&gt;&lt;apd:Line&gt;ADDRESS LINE 2&lt;/apd:Line&gt;&lt;apd:Line&gt;ADDRESS LINE 3&lt;/apd:Line&gt;&lt;apd:Line&gt;ADDRESS LINE 4&lt;/apd:Line&gt;&lt;apd:Line&gt;SOMETOWN, SOMECOUNTY&lt;/apd:Line&gt;&lt;apd:PostCode&gt;GU12 7RT&lt;/apd:PostCode&gt;&lt;/cs:Address&gt;&lt;/cs:PersonalDetails&gt;&lt;cs:ASNs&gt;&lt;cs:ASN&gt;0723XH1000000262665K&lt;/cs:ASN&gt;&lt;/cs:ASNs&gt;&lt;cs:CRESTdefendantID&gt;29161&lt;/cs:CRESTdefendantID&gt;&lt;cs:PNCnumber&gt;20123456789L&lt;/cs:PNCnumber&gt;&lt;cs:URN&gt;62AA1010646&lt;/cs:URN&gt;&lt;cs:CustodyStatus&gt;In custody&lt;/cs:CustodyStatus&gt;&lt;/cs:Defendant&gt;&lt;/cs:Defendants&gt;&lt;/cs:Hearing&gt;&lt;/cs:Hearings&gt;&lt;/cs:Sitting&gt;&lt;/cs:Sittings&gt;&lt;/cs:CourtList&gt;&lt;/cs:CourtLists&gt;&lt;/cs:DailyList&gt;</document>\n"
        + "      </ns5:addDocument>\n"
        + "   </s:Body>\n"
        + "</s:Envelope>",
        registrationToken, eventType, eventType, courtHouseType, courtHouseCode, courtHouseName, courtHouseType, courtHouseCode, courtHouseName, courtHouseType, courtHouseCode, courtHouseName);        
    }

    public static String addDocumentCPPDailyListTokenRequest(Session session, NumberGenerator generator) {

        // Retrieve values from session or define defaults if needed
        String registrationToken = session.get("registrationToken") != null
            ? session.get("registrationToken").toString()
            : "";
        String courtHouseType = session.get("display_name") != null
            ? session.get("display_name").toString()
            : "";
        String courtHouseName = session.get("courthouse_name") != null
            ? session.get("courthouse_name").toString()
            : "";
        String courtHouseCode = session.get("courthouse_code") != null
            ? session.get("courthouse_code").toString()
            : "";
         
        // Generate dynamic values
        String uniqueId = generator.generateNextNumber();
        RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
        String documentName = randomStringGenerator.generateRandomString(10);
        RandomStringGenerator randomStringGenerator2 = new RandomStringGenerator();
        String uniqueIdName = randomStringGenerator2.generateRandomString(15);

        String getCurrentTimestamp = TimestampGenerator.getCurrentTimestamp();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");

        // Get the current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Format just the date
        String formattedDate = currentDateTime.toLocalDate().format(dateFormatter);

        // Format the date and time
        String formattedDateTime = currentDateTime.format(dateTimeFormatter);


        // Construct SOAP request
        return String.format("<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\">\n"
        + "   <s:Header>\n"
        + "      <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">\n"
        + "         <wsse:BinarySecurityToken QualificationValueType=\"http://schemas.emc.com/documentum#ResourceAccessToken\" "
        + "                                   xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" "
        + "                                   wsu:Id=\"RAD\">%s</wsse:BinarySecurityToken>\n"
        + "      </wsse:Security>\n"
        + "   </s:Header>\n"
        + "   <s:Body>\n"
        + "      <ns5:addDocument xmlns:ns5=\"http://com.synapps.mojdarts.service.com\">\n"
        + "         <messageId>257202</messageId>\n"
        + "         <type>CPPDL</type>\n"
        + "         <subType>CPPDL</subType>\n"
        + "         <document><![CDATA[\n"
        + "             <cs:DailyList xmlns:cs=\"http://www.courtservice.gov.uk/schemas/courtservice\" "
        + "                           xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
        + "                           xsi:schemaLocation=\"http://www.courtservice.gov.uk/schemas/courtservice DailyList-v5-2.xsd\" "
        + "                           xmlns:apd=\"http://www.govtalk.gov.uk/people/AddressAndPersonalDetails\">\n"
        + "             <cs:DocumentID>\n"
        + "                  <cs:DocumentName>Perf_Doc_CPP_" + documentName + "</cs:DocumentName>\n"
        + "                  <cs:UniqueID>Perf_" + uniqueIdName + "_" + uniqueId + "</cs:UniqueID>\n"
        + "                  <cs:DocumentType>CPPDL</cs:DocumentType>\n"
        + "                  <cs:TimeStamp>" + getCurrentTimestamp + "</cs:TimeStamp>\n"
        + "                  <cs:Version>1.0</cs:Version>\n"
        + "                  <cs:SecurityClassification>NPM</cs:SecurityClassification>\n"
        + "                  <cs:SellByDate>2010-12-15</cs:SellByDate>\n"
        + "                  <cs:XSLstylesheetURL>http://www.courtservice.gov.uk/transforms/courtservice/dailyListHtml.xsl</cs:XSLstylesheetURL>\n"
        + "             </cs:DocumentID>\n"
        + "             <cs:ListHeader>\n"
        + "                  <cs:ListCategory>Criminal</cs:ListCategory>\n"
        + "                  <cs:StartDate>"+ formattedDate +"</cs:StartDate>\n"
        + "                  <cs:EndDate>" + formattedDate + "</cs:EndDate>\n"
        + "                  <cs:Version>FINAL v1</cs:Version>\n"
        + "                  <cs:CRESTprintRef>MCD/112585</cs:CRESTprintRef>\n"
        + "                  <cs:PublishedTime>" + formattedDateTime + "</cs:PublishedTime>\n"
        + "                  <cs:CRESTlistID>12298</cs:CRESTlistID>\n"
        + "             </cs:ListHeader>\n"
        + "             <cs:CrownCourt>\n"
        + "                  <cs:CourtHouseType>%s</cs:CourtHouseType>\n"
        + "                  <cs:CourtHouseCode CourtHouseShortName=\"SNARE\">%s</cs:CourtHouseCode>\n"
        + "                  <cs:CourtHouseName>%s</cs:CourtHouseName>\n"
        + "                  <cs:CourtHouseAddress>\n"
        + "                  <apd:Line>THE CROWN COURT AT Bristol</apd:Line>\n"
        + "                  <apd:Line>75 HOLLYBUSH HILL</apd:Line>\n"
        + "                  <apd:Line>Bristol, LONDON</apd:Line>\n"
        + "                  <apd:PostCode>E11 1QW</apd:PostCode>\n"
        + "                  </cs:CourtHouseAddress>\n"
        + "                  <cs:CourtHouseDX>DX 98240 WANSTEAD 2</cs:CourtHouseDX>\n"
        + "                  <cs:CourtHouseTelephone>02085300000</cs:CourtHouseTelephone>\n"
        + "                  <cs:CourtHouseFax>02085300072</cs:CourtHouseFax>\n"
        + "            </cs:CrownCourt>\n"
        + "              <cs:CourtLists>\n"
        + "                    <cs:CourtList>\n"
        + "                         <cs:CourtHouse>\n"
        + "                         <cs:CourtHouseType>%s</cs:CourtHouseType>\n"
        + "                         <cs:CourtHouseCode>%s</cs:CourtHouseCode>\n"
        + "                         <cs:CourtHouseName>%s</cs:CourtHouseName>\n"
        + "                         </cs:CourtHouse>\n"
        + "                         <cs:Sittings>\n"
        + "                             <cs:Sitting>\n"
        + "                                 <cs:CourtRoomNumber>1</cs:CourtRoomNumber>\n"
        + "                                 <cs:SittingSequenceNo>1</cs:SittingSequenceNo>\n"
        + "                                 <cs:SittingAt>10:00:00</cs:SittingAt>\n"
        + "                                 <cs:SittingPriority>T</cs:SittingPriority>\n"
        + "                             <cs:Judiciary>\n"
        + "                                 <cs:Judge>\n"
        + "                                     <apd:CitizenNameSurname>N/A</apd:CitizenNameSurname>\n"
        + "                                     <apd:CitizenNameRequestedName>N/A</apd:CitizenNameRequestedName>\n"
        + "                                     <cs:CRESTjudgeID>0</cs:CRESTjudgeID>\n"
        + "                                 </cs:Judge>\n"
        + "                             </cs:Judiciary>\n"
        + "                                     <cs:Hearings>\n"
        + "                                         <cs:Hearing>\n"
        + "                                             <cs:HearingSequenceNumber>1</cs:HearingSequenceNumber>\n"
        + "                                             <cs:HearingDetails HearingType=\"TRL\">\n"
        + "                                             <cs:HearingDescription>For Trial</cs:HearingDescription>\n"
        + "                                             <cs:HearingDate>" + formattedDate + "</cs:HearingDate>\n"
        + "                                             </cs:HearingDetails>\n"
        + "                                             <cs:CRESThearingID>1</cs:CRESThearingID>\n"
        + "                                             <cs:TimeMarkingNote>10:00 AM</cs:TimeMarkingNote>\n"
        + "                                             <cs:CaseNumber>Perf_Case_"+ documentName +"</cs:CaseNumber>\n"
        + "                                         <cs:Prosecution ProsecutingAuthority=\"Crown Prosecution Service\">\n"
        + "                                             <cs:ProsecutingReference>CPS</cs:ProsecutingReference>\n"
        + "                                             <cs:ProsecutingOrganisation>\n"
        + "                                                 <cs:OrganisationName>Crown Prosecution Service</cs:OrganisationName>\n"
        + "                                             </cs:ProsecutingOrganisation>\n"
        + "                                         </cs:Prosecution>\n"
        + "                                         <cs:CommittingCourt>\n"
        + "                                             <cs:CourtHouseType>%s</cs:CourtHouseType>\n"
        + "                                             <cs:CourtHouseCode CourtHouseShortName=\"BAM\">%s</cs:CourtHouseCode>\n"
        + "                                             <cs:CourtHouseName>%s</cs:CourtHouseName>\n"
        + "                                             <cs:CourtHouseAddress>\n"
        + "                                               <apd:Line>7C HIGH STREET</apd:Line>\n"
        + "                                              <apd:Line>-</apd:Line>\n"
        + "                                                 <apd:Line>BARNET</apd:Line>\n"
        + "                                              <apd:PostCode>EN5 5UE</apd:PostCode>\n"
        + "                                          </cs:CourtHouseAddress>\n"
        + "                                             <cs:CourtHouseDX>DX 8626 BARNET</cs:CourtHouseDX>\n"
        + "                                             <cs:CourtHouseTelephone>02084419042</cs:CourtHouseTelephone>\n"
        + "                                         </cs:CommittingCourt>\n"
        + "                                        <cs:Defendants>\n"
        + "                                         <cs:Defendant>\n"
        + "                                             <cs:PersonalDetails>\n"
        + "                                                 <cs:Name>\n"
        + "                                                     <apd:CitizenNameForename>Franz</apd:CitizenNameForename>\n"
        + "                                                     <apd:CitizenNameSurname>KAFKA</apd:CitizenNameSurname>\n"
        + "                                                 </cs:Name>\n"
        + "                                                 <cs:IsMasked>no</cs:IsMasked>\n"
        + "                                                 <cs:DateOfBirth>\n"
        + "                                                     <apd:BirthDate>1962-06-12</apd:BirthDate>\n"
        + "                                                     <apd:VerifiedBy>not verified</apd:VerifiedBy>\n"
        + "                                                 </cs:DateOfBirth>\n"
        + "                                                 <cs:Sex>male</cs:Sex>\n"
        + "                                                 <cs:Address>\n"
        + "                                                     <apd:Line>ADDRESS LINE 1</apd:Line>\n"
        + "                                                     <apd:Line>ADDRESS LINE 2</apd:Line>\n"
        + "                                                     <apd:Line>ADDRESS LINE 3</apd:Line>\n"
        + "                                                     <apd:Line>ADDRESS LINE 4</apd:Line>\n"
        + "                                                     <apd:Line>SOMETOWN, SOMECOUNTY</apd:Line>\n"
        + "                                                     <apd:PostCode>GU12 7RT</apd:PostCode>\n"
        + "                                                 </cs:Address>\n"
        + "                                             </cs:PersonalDetails>\n"
        + "                                             <cs:ASNs>\n"
        + "                                                 <cs:ASN>0723XH1000000262665K</cs:ASN>\n"
        + "                                             </cs:ASNs>\n"
        + "                                             <cs:CRESTdefendantID>29161</cs:CRESTdefendantID>\n"
        + "                                             <cs:PNCnumber>20123456789L</cs:PNCnumber>\n"
        + "                                             <cs:URN>62AA1010646</cs:URN>\n"
        + "                                             <cs:CustodyStatus>In custody</cs:CustodyStatus>\n"
        + "                                             </cs:Defendant>\n"
        + "                                         </cs:Defendants>\n"
        + "                                     </cs:Hearing>\n"
        + "                                 </cs:Hearings>\n"
        + "                             </cs:Sitting>\n"
        + "                         </cs:Sittings>\n"
        + "                         </cs:CourtList>\n"
        + "                     </cs:CourtLists>\n"
        + "                 </cs:DailyList>]]>\n"
        + "             </document>\n"
        + "      </ns5:addDocument>\n"
        + "   </s:Body>\n"
        + "</s:Envelope>",
        registrationToken, courtHouseType, courtHouseCode, courtHouseName, courtHouseType, courtHouseCode, courtHouseName, courtHouseType, courtHouseCode, courtHouseName);        
    }

    public static String addDocumentXhibitDailyListTokenRequest(Session session, NumberGenerator generator) {

        // Retrieve values from session or define defaults if needed
        String registrationToken = session.get("registrationToken") != null
            ? session.get("registrationToken").toString()
            : "";
        String courtHouseType = session.get("display_name") != null
            ? session.get("display_name").toString()
            : "";
        String courtHouseName = session.get("courthouse_name") != null
            ? session.get("courthouse_name").toString()
            : "";
        String courtHouseCode = session.get("courthouse_code") != null
            ? session.get("courthouse_code").toString()
            : "";
         
        // Generate dynamic values
        String uniqueId = generator.generateNextNumber();
        RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
        String documentName = randomStringGenerator.generateRandomString(10);
        
        RandomStringGenerator randomStringGenerator2 = new RandomStringGenerator();
        String uniqueIdName = randomStringGenerator2.generateRandomString(15);

        String getCurrentTimestamp = TimestampGenerator.getCurrentTimestamp();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");

        // Get the current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Format just the date
        String formattedDate = currentDateTime.toLocalDate().format(dateFormatter);

        // Format the date and time
        String formattedDateTime = currentDateTime.format(dateTimeFormatter);

        // Construct SOAP request
        return String.format(
            "<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\">\n"
            + "   <s:Header>\n"
            + "      <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">\n"
            + "         <wsse:BinarySecurityToken QualificationValueType=\"http://schemas.emc.com/documentum#ResourceAccessToken\" "
            + "                                   xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" "
            + "                                   wsu:Id=\"RAD\">%s</wsse:BinarySecurityToken>\n"
            + "      </wsse:Security>\n"
            + "   </s:Header>\n"
            + "   <s:Body>\n"
            + "      <ns5:addDocument xmlns:ns5=\"http://com.synapps.mojdarts.service.com\">\n"
            + "         <messageId>18418</messageId>\n"
            + "         <type>DL</type>\n"
            + "         <subType>DL</subType>\n"
            + "         <document><![CDATA[\n"
            + "            <cs:DailyList xmlns:cs=\"http://www.courtservice.gov.uk/schemas/courtservice\" "
            + "                          xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
            + "                          xsi:schemaLocation=\"http://www.courtservice.gov.uk/schemas/courtservice DailyList-v5-2.xsd\" "
            + "                          xmlns:apd=\"http://www.govtalk.gov.uk/people/AddressAndPersonalDetails\">\n"
            + "               <cs:DocumentID>\n"
            + "                  <cs:DocumentName>Perf_Doc_Xhibit_" + documentName + "</cs:DocumentName>\n"
            + "                  <cs:UniqueID>Perf_" + uniqueIdName + "_" + uniqueId + "</cs:UniqueID>\n"
            + "                  <cs:DocumentType>DL</cs:DocumentType>\n"
            + "                  <cs:TimeStamp>" + getCurrentTimestamp + "</cs:TimeStamp>\n"
            + "                  <cs:Version>1.0</cs:Version>\n"
            + "                  <cs:SecurityClassification>NPM</cs:SecurityClassification>\n"
            + "                  <cs:SellByDate>2010-12-15</cs:SellByDate>\n"
            + "                  <cs:XSLstylesheetURL>http://www.courtservice.gov.uk/transforms/courtservice/dailyListHtml.xsl</cs:XSLstylesheetURL>\n"
            + "               </cs:DocumentID>\n"
            + "               <cs:ListHeader>\n"
            + "                  <cs:ListCategory>Criminal</cs:ListCategory>\n"
            + "                  <cs:StartDate>" + formattedDate + "</cs:StartDate>\n"
            + "                  <cs:EndDate>" + formattedDate + "</cs:EndDate>\n"
            + "                  <cs:Version>FINAL v1</cs:Version>\n"
            + "                  <cs:CRESTprintRef>MCD/112585</cs:CRESTprintRef>\n"
            + "                  <cs:PublishedTime>" + formattedDateTime + "</cs:PublishedTime>\n"
            + "                  <cs:CRESTlistID>12298</cs:CRESTlistID>\n"
            + "               </cs:ListHeader>\n"
            + "               <cs:CrownCourt>\n"
            + "                  <cs:CourtHouseType>%s</cs:CourtHouseType>\n"
            + "                  <cs:CourtHouseCode CourtHouseShortName=\"SNARE\">%s</cs:CourtHouseCode>\n"
            + "                  <cs:CourtHouseName>%s</cs:CourtHouseName>\n"
            + "                  <cs:CourtHouseAddress>\n"
            + "                     <apd:Line>THE CROWN COURT AT Bristol</apd:Line>\n"
            + "                     <apd:Line>75 HOLLYBUSH HILL</apd:Line>\n"
            + "                     <apd:Line>Bristol, LONDON</apd:Line>\n"
            + "                     <apd:PostCode>E11 1QW</apd:PostCode>\n"
            + "                  </cs:CourtHouseAddress>\n"
            + "                  <cs:CourtHouseDX>DX 98240 WANSTEAD 2</cs:CourtHouseDX>\n"
            + "                  <cs:CourtHouseTelephone>02085300000</cs:CourtHouseTelephone>\n"
            + "                  <cs:CourtHouseFax>02085300072</cs:CourtHouseFax>\n"
            + "               </cs:CrownCourt>\n"
            + "            </cs:DailyList>\n"
            + "         ]]></document>\n"
            + "      </ns5:addDocument>\n"
            + "   </s:Body>\n"
            + "</s:Envelope>",
            registrationToken, courtHouseType, courtHouseCode, courtHouseName, 
            courtHouseType, courtHouseCode, courtHouseName, courtHouseType, 
            courtHouseCode, courtHouseName
        );
    }        

    public static String addDocumentEventTokenRequest(Session session) {

        String registrationToken = session.get("registrationToken") != null
            ? session.get("registrationToken").toString()
            : "";

        // Retrieve values from session or define defaults if needed
        String courtHouseName = session.get("courthouse_name") != null
            ? session.get("courthouse_name").toString()
            : "";
        String courtRoom = session.get("courtroom_name") != null
            ? session.get("courtroom_name").toString()
            : "";

        // Generate dynamic values
        RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
        String caseName = randomStringGenerator.generateRandomString(10);
        String eventText = randomStringGenerator.generateRandomString(10);
        LocalDateTime now = LocalDateTime.now();

        String currentTimeMillis = String.valueOf(System.currentTimeMillis());
        String trimmedID = currentTimeMillis.substring(4);


        // Construct SOAP request
        return String.format("<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\">\n"
        + "   <s:Header>\n"
        + "      <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">\n"
        + "         <wsse:BinarySecurityToken QualificationValueType=\"http://schemas.emc.com/documentum#ResourceAccessToken\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" wsu:Id=\"RAD\">%s</wsse:BinarySecurityToken>\n"
        + "      </wsse:Security>\n"
        + "   </s:Header>\n"
        + "       <s:Body>\n"
        + "      <ns5:addDocument xmlns:ns5=\"http://com.synapps.mojdarts.service.com\">\n"
        + "            <messageId>" + trimmedID + "</messageId>\n"
        + "            <type>2198</type>\n"
        + "            <subType>3940</subType>\n"
        + "             <document><![CDATA[<be:DartsEvent xmlns:be=\"urn:integration-cjsonline-gov-uk:pilot:entities\" ID=\""
        + trimmedID +" Y=\""
        + now.format(DateTimeFormatter.ofPattern("yyyy")) + "\" M=\""
        + now.format(DateTimeFormatter.ofPattern("MM")) + "\" D=\""
        + now.format(DateTimeFormatter.ofPattern("dd")) + "\" H=\""
        + now.format(DateTimeFormatter.ofPattern("HH")) +"\" MIN=\""
        + now.format(DateTimeFormatter.ofPattern("mm")) + "\" S=\""
        + now.format(DateTimeFormatter.ofPattern("ss")) + "\"><be:CourtHouse>%s</be:CourtHouse><be:CourtRoom>%s</be:CourtRoom><be:CaseNumbers><be:CaseNumber>%s</be:CaseNumber></be:CaseNumbers><be:EventText>%s</be:EventText></be:DartsEvent>]]></document>\n"
        + "            </ns5:addDocument>\n"
        + "        </s:Body>\n"
        + "     </s:Envelope>",
        registrationToken, courtHouseName, courtRoom, caseName, eventText);        
    }

    public static String addDocumentCPPEventTokenRequest(Session session) {

        String registrationToken = session.get("registrationToken") != null
            ? session.get("registrationToken").toString()
            : "";

        // Retrieve values from session or define defaults if needed
        String courtHouseName = session.get("courthouse_name") != null
            ? session.get("courthouse_name").toString()
            : "";
        String courtRoom = session.get("courtroom_name") != null
            ? session.get("courtroom_name").toString()
            : "";

        // Generate dynamic values
        RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
        String caseName = randomStringGenerator.generateRandomString(5);
        String eventText = "Add Document CPP Event -" + randomStringGenerator.generateRandomString(10);
        LocalDateTime now = LocalDateTime.now();

        String currentTimeMillis = String.valueOf(System.currentTimeMillis());
        String trimmedID = currentTimeMillis.substring(4);

        // Construct SOAP request
        return String.format(
            "<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\">\n"
                + "   <s:Header>\n"
                + "      <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">\n"
                + "         <wsse:BinarySecurityToken QualificationValueType=\"http://schemas.emc.com/documentum#ResourceAccessToken\" "
                + "xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" "
                + "wsu:Id=\"RAD\">%s</wsse:BinarySecurityToken>\n"
                + "      </wsse:Security>\n"
                + "   </s:Header>\n"
                + "   <s:Body>\n"
                + "      <ns5:addDocument xmlns:ns5=\"http://com.synapps.mojdarts.service.com\">\n"
                + "            <messageId>" + trimmedID + "</messageId>\n"
                + "            <type>2198</type>\n"
                + "            <subType>3940</subType>\n"
                + "            <document>&lt;be:DartsEvent xmlns:be=&quot;urn:integration-cjsonline-gov-uk:pilot:entities&quot; "
                + "ID=&quot;" + trimmedID + " &quot; Y=&quot;"
                + now.format(DateTimeFormatter.ofPattern("yyyy")) + "&quot; M=&quot;"
                + now.format(DateTimeFormatter.ofPattern("MM")) + "&quot; D=&quot;"
                + now.format(DateTimeFormatter.ofPattern("dd")) + "&quot; H=&quot;"
                + now.format(DateTimeFormatter.ofPattern("HH")) + "&quot; MIN=&quot;"
                + now.format(DateTimeFormatter.ofPattern("mm")) + "&quot; S=&quot;"
                + now.format(DateTimeFormatter.ofPattern("ss")) + "&quot;&gt;&lt;be:CourtHouse&gt;"
                + "%s&lt;/be:CourtHouse&gt;&lt;be:CourtRoom&gt;"
                + "%s&lt;/be:CourtRoom&gt;&lt;be:CaseNumbers&gt;&lt;be:CaseNumber&gt;"
                + "Perf_DocCPPEvent_%s&lt;/be:CaseNumber&gt;&lt;/be:CaseNumbers&gt;&lt;be:EventText&gt;"
                + "%s&lt;/be:EventText&gt;&lt;/be:DartsEvent&gt;</document>\n"
                + "            </ns5:addDocument>\n"
                + "   </s:Body>\n"
                + "</s:Envelope>",
            registrationToken, courtHouseName, courtRoom, caseName, eventText
        );
        
    }

    public static String addDocumentXhibitEventTokenRequest(Session session) {

        String registrationToken = session.get("registrationToken") != null
            ? session.get("registrationToken").toString()
            : "";

        // Retrieve values from session or define defaults if needed
        String courtHouseName = session.get("courthouse_name") != null
            ? session.get("courthouse_name").toString()
            : "";
        String courtRoom = session.get("courtroom_name") != null
            ? session.get("courtroom_name").toString()
            : "";

        // Generate dynamic values
        RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
        String caseName = randomStringGenerator.generateRandomString(5);
        String eventText =  "Add Document Xhibit -" + randomStringGenerator.generateRandomString(10);
        LocalDateTime now = LocalDateTime.now();
        String currentTimeMillis = String.valueOf(System.currentTimeMillis());
        String trimmedID = currentTimeMillis.substring(4);
        
        // Construct SOAP request
        return String.format(
            "<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\">\n"
                + "   <s:Header>\n"
                + "      <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">\n"
                + "         <wsse:BinarySecurityToken QualificationValueType=\"http://schemas.emc.com/documentum#ResourceAccessToken\" "
                + "xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" wsu:Id=\"RAD\">%s</wsse:BinarySecurityToken>\n"
                + "      </wsse:Security>\n"
                + "   </s:Header>\n"
                + "   <s:Body>\n"
                + "      <ns5:addDocument xmlns:ns5=\"http://com.synapps.mojdarts.service.com\">\n"
                + "         <messageId>" + trimmedID + "</messageId>\n"
                + "         <type>2198</type>\n"
                + "         <subType>3940</subType>\n"
                + "         <document>&lt;be:DartsEvent xmlns:be=&quot;urn:integration-cjsonline-gov-uk:pilot:entities&quot; ID=&quot;" 
                + trimmedID + "&quot; Y=&quot;" 
                + now.format(DateTimeFormatter.ofPattern("yyyy")) + "&quot; M=&quot;" 
                + now.format(DateTimeFormatter.ofPattern("MM")) + "&quot; D=&quot;" 
                + now.format(DateTimeFormatter.ofPattern("dd")) + "&quot; H=&quot;" 
                + now.format(DateTimeFormatter.ofPattern("HH")) + "&quot; MIN=&quot;" 
                + now.format(DateTimeFormatter.ofPattern("mm")) + "&quot; S=&quot;" 
                + now.format(DateTimeFormatter.ofPattern("ss")) + "&quot;&gt;&lt;be:CourtHouse&gt;%s&lt;/be:CourtHouse&gt;&lt;be:CourtRoom&gt;%s&lt;/be:CourtRoom&gt;&lt;be:CaseNumbers&gt;&lt;be:CaseNumber&gt;Perf_DocXhibitEvent_%s&lt;/be:CaseNumber&gt;&lt;/be:CaseNumbers&gt;&lt;be:EventText&gt;%s&lt;/be:EventText&gt;&lt;/be:DartsEvent&gt;</document>\n"
                + "      </ns5:addDocument>\n"
                + "   </s:Body>\n"
                + "</s:Envelope>",
            registrationToken, courtHouseName, courtRoom, caseName, eventText
        );              
    }

    public static String getCasesUserRequest(Session session, String userName, String password) {
        // Retrieve values from session or define defaults if needed
        String courtHouseName = session.get("courthouse_name") != null
            ? session.get("courthouse_name").toString()
            : "";
        String courtRoom = session.get("courtroom_name") != null
            ? session.get("courtroom_name").toString()
            : "";   

        // Construct SOAP request
        return String.format(
            "<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\">\n"
                + "   <s:Header>\n"
                + "      <ServiceContext token=\"temporary/127.0.0.1-1694086218480-789961425\" "
                + "xmlns=\"http://context.core.datamodel.fs.documentum.emc.com/\">\n"
                + "         <Identities xsi:type=\"RepositoryIdentity\" userName=\"%s\" password=\"%s\" "
                + "repositoryName=\"moj_darts\" domain=\"\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>\n"
                + "         <RuntimeProperties/>\n"
                + "      </ServiceContext>\n"
                + "   </s:Header>\n"
                + "   <s:Body xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n"
                + "      <ns2:getCases xmlns:ns2=\"http://com.synapps.mojdarts.service.com\">\n"
                + "         <courthouse>%s</courthouse>\n"
                + "         <courtroom>%s</courtroom>\n"
                + "         <date>2023-11-29</date>\n"
                + "      </ns2:getCases>\n"
                + "   </s:Body>\n"
                + "</s:Envelope>",
            userName, password, courtHouseName, courtRoom
        );        
    }    
  
    public static String getCasesTokenRequest(Session session) {
        // Retrieve values from session or define defaults if needed
        String registrationToken = session.get("registrationToken") != null
        ? session.get("registrationToken").toString() : "";
        
        String courtHouseName = session.get("courthouse_name") != null 
        ? session.get("courthouse_name").toString() : "";
        
        String courtRoom = session.get("courtroom_name") != null 
        ? session.get("courtroom_name").toString() : "";

    // Construct SOAP request
    return String.format(
        "<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\">\n"
        + "   <s:Header>\n"
        + "      <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">\n"
        + "         <wsse:BinarySecurityToken QualificationValueType=\"http://schemas.emc.com/documentum#ResourceAccessToken\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" wsu:Id=\"RAD\">%s</wsse:BinarySecurityToken>\n"
        + "      </wsse:Security>\n"
        + "   </s:Header>\n"
        + "   <s:Body xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n"
        + "      <ns2:getCases xmlns:ns2=\"http://com.synapps.mojdarts.service.com\">\n"
        + "         <courthouse>%s</courthouse>\n"
        + "         <courtroom>%s</courtroom>\n"
        + "         <date>2017-11-29</date>\n"
        + "      </ns2:getCases>\n"
        + "   </s:Body>\n"
        + "</s:Envelope>",
        registrationToken, courtHouseName, courtRoom);  
    }
    public static String addCaseUserRequest(Session session, String userName, String password) {
        // Retrieve values from session or define defaults if needed
        String courtHouseName = session.get("courthouse_name")
            != null ? session.get("courthouse_name").toString()
            : "";
        String courtRoom = session.get("courtroom_name") != null
            ? session.get("courtroom_name").toString()
            : "";

        // Generate dynamic values
        RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
        String id = randomStringGenerator.generateRandomString(5);
        String defendantName = randomStringGenerator.generateRandomString(10);
        String defendantName2 = randomStringGenerator.generateRandomString(10);
        String judgeName = randomStringGenerator.generateRandomString(10);
        String judgeName2 = randomStringGenerator.generateRandomString(10);
        String prosecutor = randomStringGenerator.generateRandomString(10);
        String prosecutor2 = randomStringGenerator.generateRandomString(10);

    // Construct SOAP request
    return String.format(
        "<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\">\n"
        + "  <s:Header>\n"
        + "    <ServiceContext token=\"temporary/127.0.0.1-1694086218480-789961425\" xmlns=\"http://context.core.datamodel.fs.documentum.emc.com/\">\n"
        + "      <Identities xsi:type=\"RepositoryIdentity\" userName=\"%s\" password=\"%s\" repositoryName=\"moj_darts\" domain=\"\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>\n"
        + "      <RuntimeProperties/>\n"
        + "    </ServiceContext>\n"
        + "  </s:Header>\n"
        + "  <s:Body xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n"
        + "    <ns2:addCase xmlns:ns2=\"http://com.synapps.mojdarts.service.com\">\n"
        + "      <document>\n"
        + "            &lt;case type=&quot;1&quot; id=&quot;Perf_AddCase_%s&quot;&gt;\n"
        + "                &lt;courthouse&gt;%s&lt;/courthouse&gt;\n"
        + "                &lt;courtroom&gt;%s&lt;/courtroom&gt;\n"
        + "                &lt;defendants&gt;\n"
        + "                   &lt;defendant&gt;%s&lt;/defendant&gt;\n"
        + "                   &lt;defendant&gt;%s&lt;/defendant&gt;\n"
        + "                &lt;/defendants&gt;\n"
        + "                &lt;judges&gt;\n"
        + "                   &lt;judge&gt;%s&lt;/judge&gt;\n"
        + "                   &lt;judge&gt;%s&lt;/judge&gt;\n"
        + "                &lt;/judges&gt;\n"
        + "                &lt;prosecutors&gt;\n"
        + "                   &lt;prosecutor&gt;%s&lt;/prosecutor&gt;\n"
        + "                   &lt;prosecutor&gt;%s&lt;/prosecutor&gt;\n"
        + "                &lt;/prosecutors&gt;\n"
        + "            &lt;/case&gt;\n"
        + "       </document>\n"
        + "    </ns2:addCase>\n"
        + "  </s:Body>\n"
        + "</s:Envelope>",
        userName, password, id, courtHouseName, courtRoom, defendantName, defendantName2, judgeName, judgeName2, prosecutor, prosecutor2);
    }

    public static String addCaseTokenRequest(Session session) {
        // Retrieve values from session or define defaults if needed
        String courtHouseName = session.get("courthouse_name") != null
            ? session.get("courthouse_name").toString()
            : "";
        String courtRoom = session.get("courtroom_name") != null
            ? session.get("courtroom_name").toString()
            : "";   
        String registrationToken = session.get("registrationToken");

        // Generate dynamic values
        RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
        String defendantName = randomStringGenerator.generateRandomString(10);
        String defendantName2 = randomStringGenerator.generateRandomString(10);
        String judgeName = randomStringGenerator.generateRandomString(10);
        String judgeName2 = randomStringGenerator.generateRandomString(10);
        String prosecutor = randomStringGenerator.generateRandomString(10);
        String prosecutor2 = randomStringGenerator.generateRandomString(10);

    // Construct SOAP request
    return String.format(
        "<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\">\n"
        + "    <s:Header>\n"
        + "        <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">\n"
        + "            <wsse:BinarySecurityToken QualificationValueType=\"http://schemas.emc.com/documentum#ResourceAccessToken\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" wsu:Id=\"RAD\">%s</wsse:BinarySecurityToken>\n"
        + "        </wsse:Security>\n"
        + "    </s:Header>\n"
        + "  <s:Body xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n"
        + "    <ns2:addCase xmlns:ns2=\"http://com.synapps.mojdarts.service.com\">\n"
        + "      <document>&lt;case type=\\&quot;1\\&quot; id=\\&quot;U20231129-1733\\&quot;&gt;&lt;courthouse&gt;%s&lt;/courthouse&gt;&lt;courtroom&gt;%s&lt;/courtroom&gt;&lt;defendants&gt;&lt;defendant&gt;%s&lt;/defendant&gt;&lt;defendant&gt;%s&lt;/defendant&gt;&lt;/defendants&gt;&lt;judges&gt;&lt;judge&gt;%s&lt;/judge&gt;&lt;judge&gt;%s Judge&lt;/judge&gt;&lt;/judges&gt;&lt;prosecutors&gt;&lt;prosecutor&gt;%s Prosecutor&lt;/prosecutor&gt;&lt;prosecutor&gt;%s Prosecutor&lt;/prosecutor&gt;&lt;/prosecutors&gt;&lt;/case&gt;</document>\n"
        + "    </ns2:addCase>\n"
        + "  </s:Body>\n"
        + "</s:Envelope>",
        registrationToken, courtHouseName, courtRoom, defendantName, defendantName2, judgeName, judgeName2, prosecutor, prosecutor2);
    }

    public static String addAudioUserRequest(Session session, String userName, String password) {
            // Retrieve values from session or define defaults if needed
            String courtHouseName = session.get("courthouse_name") != null
                ? session.get("courthouse_name").toString()
                : "";
            String courtRoom = session.get("courtroom_name") != null
                ? session.get("courtroom_name").toString()
                : "";
            RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
            String caseName = randomStringGenerator.generateRandomString(10);

    // Construct SOAP request
    return String.format(
        "<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:com=\"http://com.synapps.mojdarts.service.com\" xmlns:core=\"http://core.datamodel.fs.documentum.emc.com/\" xmlns:prop=\"http://properties.core.datamodel.fs.documentum.emc.com/\" xmlns:con=\"http://content.core.datamodel.fs.documentum.emc.com/\">\n"
        + "   <s:Header>\n"
        + "      <ServiceContext token=\"temporary/127.0.0.1-1694086218480-789961425\" xmlns=\"http://context.core.datamodel.fs.documentum.emc.com/\">\n"
        + "         <Identities xsi:type=\"RepositoryIdentity\" userName=\"%s\" password=\"%s\" repositoryName=\"moj_darts\" domain=\"\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>\n"
        + "         <RuntimeProperties/>\n"
        + "      </ServiceContext>\n"
        + "   </s:Header>\n"
        + "   <s:Body>\n"
        + "      <com:addAudio>\n"
        + "         <!--Optional:-->\n"
        + "         <document>&lt;audio&gt;&lt;start Y=\"2023\" M=\"9\" D=\"7\" H=\"11\" MIN=\"26\" S=\"51\" /&gt;&lt;end Y=\"2023\" M=\"9\" D=\"7\" H=\"11\" MIN=\"29\" S=\"49\" /&gt;&lt;channel&gt;1&lt;/channel&gt;&lt;max_channels&gt;4&lt;/max_channels&gt;&lt;mediaformat&gt;mpeg2&lt;/mediaformat&gt;&lt;mediafile&gt;0001.a00&lt;/mediafile&gt;&lt;courthouse&gt;%s&lt;/courthouse&gt;&lt;courtroom&gt;%s&lt;/courtroom&gt;&lt;case_numbers&gt;&lt;case_number&gt;%s&lt;/case_number&gt;&lt;case_number&gt;test&lt;/case_number&gt;&lt;/case_numbers&gt;&lt;/audio&gt;</document>\n"
        + "      </com:addAudio>\n"
        + "   </s:Body>\n"
        + "</s:Envelope>",
        userName, password, courtHouseName, courtRoom, caseName);
    }    
    public static String addAudioTokenRequest(Session session, String randomAudioFile) {
        // Retrieve values from session or define defaults if needed
        String registrationToken = session.get("registrationToken");
        String courtHouseName = session.get("courthouse_name") != null
            ? session.get("courthouse_name").toString()
            : "";
        String courtRoom = session.get("courtroom_name") != null
            ? session.get("courtroom_name").toString()
            : "";
        RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
        String caseName = randomStringGenerator.generateRandomString(10);
        String caseName2 = randomStringGenerator.generateRandomString(10);

        // Construct SOAP request
        return String.format(
            "<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:com=\"http://com.synapps.mojdarts.service.com\" xmlns:core=\"http://core.datamodel.fs.documentum.emc.com/\" xmlns:prop=\"http://properties.core.datamodel.fs.documentum.emc.com/\" xmlns:con=\"http://content.core.datamodel.fs.documentum.emc.com/\">\n"
            + "    <s:Header>\n"
            + "        <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">\n"
            + "            <wsse:BinarySecurityToken QualificationValueType=\"http://schemas.emc.com/documentum#ResourceAccessToken\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" wsu:Id=\"RAD\">%s</wsse:BinarySecurityToken>\n"
            + "        </wsse:Security>\n"
            + "    </s:Header>\n"
            + "   <s:Body>\n"
            + "      <com:addAudio>\n"
            + "         <document>&lt;audio&gt;&lt;start Y=&quot;2023&quot; M=&quot;9&quot; D=&quot;7&quot; H=&quot;11&quot; MIN=&quot;26&quot; S=&quot;51&quot;&gt;&lt;end Y=&quot;2023&quot; M=&quot;9&quot; D=&quot;7&quot; H=&quot;11&quot; MIN=&quot;29&quot; S=&quot;49&quot;&gt;&lt;channel&gt;1&lt;/channel&gt;&lt;max_channels&gt;4&lt;/max_channels&gt;&lt;mediaformat&gt;mpeg2&lt;/mediaformat&gt;&lt;mediafile&gt;"+ randomAudioFile +"&lt;/mediafile&gt;&lt;courthouse&gt;%s&lt;/courthouse&gt;&lt;courtroom&gt;%s&lt;/courtroom&gt;&lt;case_numbers&gt;&lt;case_number&gt;%s&lt;/case_number&gt;&lt;case_number&gt;%s&lt;/case_number&gt;&lt;/case_numbers&gt;&lt;/audio&gt;</document>\n"
            + "      </com:addAudio>\n"
            + "   </s:Body>\n"
            + "</s:Envelope>",
            registrationToken, courtHouseName, courtRoom, caseName, caseName2);
        }    

        public static String addAudioTokenRequestBinary (Session session, String randomAudioFile) {
            // Retrieve values from session or define defaults if needed
            String registrationToken = session.get("registrationToken");
            String courtHouseName = session.get("courthouse_name") != null
                ? session.get("courthouse_name").toString()
                : "";
            String courtRoom = session.get("courtroom_name") != null
                ? session.get("courtroom_name").toString()
                : "";
            RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
            String caseName = randomStringGenerator.generateRandomString(10);
            String caseName2 = randomStringGenerator.generateRandomString(10);
        
            // Construct SOAP request with xop:Include for binary attachment
            return String.format(
                "<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\" "
                + "xmlns:com=\"http://com.synapps.mojdarts.service.com\" "
                + "xmlns:core=\"http://core.datamodel.fs.documentum.emc.com/\" "
                + "xmlns:prop=\"http://properties.core.datamodel.fs.documentum.emc.com/\" "
                + "xmlns:con=\"http://content.core.datamodel.fs.documentum.emc.com/\" "
                + "xmlns:xop=\"http://www.w3.org/2004/08/xop/include\">"
                + "    <s:Header>"
                + "        <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
                + "            <wsse:BinarySecurityToken QualificationValueType=\"http://schemas.emc.com/documentum#ResourceAccessToken\" "
                + "               xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" wsu:Id=\"RAD\">"
                + "%s</wsse:BinarySecurityToken>"
                + "        </wsse:Security>"
                + "    </s:Header>"
                + "    <s:Body>"
                + "        <com:addAudio>"
                + "            <document>"
                + "                <audio>"
                + "                    <start Y=\"2023\" M=\"9\" D=\"7\" H=\"11\" MIN=\"26\" S=\"51\"/>"
                + "                    <end Y=\"2023\" M=\"9\" D=\"7\" H=\"11\" MIN=\"29\" S=\"49\"/>"
                + "                    <channel>1</channel>"
                + "                    <max_channels>4</max_channels>"
                + "                    <mediaformat>mpeg2</mediaformat>"
                + "                    <mediafile><xop:Include href=\"cid:%s\"/></mediafile>"
                + "                    <courthouse>%s</courthouse>"
                + "                    <courtroom>%s</courtroom>"
                + "                    <case_numbers>"
                + "                        <case_number>%s</case_number>"
                + "                        <case_number>%s</case_number>"
                + "                    </case_numbers>"
                + "                </audio>"
                + "            </document>"
                + "        </com:addAudio>"
                + "    </s:Body>"
                + "</s:Envelope>",
                registrationToken, randomAudioFile, courtHouseName, courtRoom, caseName, caseName2
            );
        }
        

    public static String addCourtLogUserRequest(Session session, String userName, String password) {
        // Retrieve values from session or define defaults if needed
        String courtHouseName = session.get("courthouse_name") != null
            ? session.get("courthouse_name").toString() 
            : "";
        String courtRoom = session.get("courtroom_name") != null
            ? session.get("courtroom_name").toString() 
            : "";
        RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
        String caseName = randomStringGenerator.generateRandomString(5);
        String randomLogText = randomStringGenerator.generateRandomString(10);

    // Construct SOAP request
    return String.format(
        "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:com=\"http://com.synapps.mojdarts.service.com\">\n"
        + "   <soapenv:Header>\n"
        + "      <ServiceContext token=\"temporary/127.0.0.1-1694086218480-789961425\" xmlns=\"http://context.core.datamodel.fs.documentum.emc.com/\">\n"
        + "         <Identities xsi:type=\"RepositoryIdentity\" userName=\"%s\" password=\"%s\" repositoryName=\"moj_darts\" domain=\"\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>\n"
        + "         <RuntimeProperties/>\n"
        + "      </ServiceContext>\n"
        + "   </soapenv:Header>\n"
        + "   <soapenv:Body>\n"
        + "      <addLogEntry xmlns=\"http://com.synapps.mojdarts.service.com\">\n"
        + "         <document xmlns=\"\">&lt;log_entry Y=&quot;2023&quot; M=&quot;01&quot; D=&quot;01&quot; H=&quot;10&quot; MIN=&quot;00&quot; S=&quot;00&quot;&gt;&lt;courthouse&gt;%s&lt;/courthouse&gt;&lt;courtroom&gt;%s&lt;/courtroom&gt;&lt;case_numbers&gt;&lt;case_number&gt;Perf_LogEntry_%s&lt;/case_number&gt;&lt;/case_numbers&gt;&lt;text&gt;%s&lt;/text&gt;&lt;/log_entry&gt;\n"
        + "         </document>\n"
        + "      </addLogEntry>\n"
        + "   </soapenv:Body>\n"
        + "</soapenv:Envelope>",
        userName, password, courtHouseName, courtRoom, caseName, randomLogText);
    } 

    public static String addCourtLogTokenRequest(Session session) {
        // Retrieve values from session or define defaults if needed
        String registrationToken = session.get("registrationToken") != null
            ? session.get("registrationToken").toString() 
            : "";
        String courtHouseName = session.get("courthouse_name") != null
            ? session.get("courthouse_name").toString()
            : "";
        String courtRoom = session.get("courtroom_name") != null
            ? session.get("courtroom_name").toString() 
            : "";

        RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
        String caseName = randomStringGenerator.generateRandomString(10);
        String randomLogText = randomStringGenerator.generateRandomString(10);

    // Construct SOAP request
    return String.format(
    "    <s:Envelope\n"
    + "    <s:Header>\n"
    + "        <wsse:Security\n"
    + "            xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">\n"
    + "            <wsse:BinarySecurityToken QualificationValueType=\"http://schemas.emc.com/documentum#ResourceAccessToken\"\n"
    + "                xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" wsu:Id=\"RAD\">%s</wsse:BinarySecurityToken>\n"
    + "        </wsse:Security>\n"
    + "    </s:Header>\n"
    + "    <s:Body xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n"
    + "        <addLogEntry xmlns=\"http://com.synapps.mojdarts.service.com\">\n"
    + "            xmlns=\"http://com.synapps.mojdarts.service.com\">\n"
    + "            <document xmlns=\"\"><![CDATA[<log_entry Y=\"2023\" M=\"12\" D=\"5\" H=\"17\" MIN=\"29\" S=\"15\">\n"
    + "                <courthouse>%s</courthouse>\n"
    + "                <courtroom>%s</courtroom>\n"
    + "                <case_numbers>\n"
    + "                <case_number>Perf_%s</case_number>\n"
    + "                </case_numbers>\n"
    + "                <text>System : Perf_%s</text>\n"
    + "            </log_entry>]]></document>\n"
    + "        </addLogEntry>\n"
    + "    </s:Body>\n"
    + "</s:Envelope>",
        registrationToken, courtHouseName, courtRoom, caseName, randomLogText);
    } 

    public static String getCourtLogTokenRequest(Session session) {
        // Retrieve values from session or define defaults if needed
        String registrationToken = session.get("registrationToken") != null
            ? session.get("registrationToken").toString() 
            : "";
        String courtHouseName = session.get("courthouse_name") != null 
            ? session.get("courthouse_name").toString() 
            : "";
        String courtRoom = session.get("courtroom_name") != null 
            ? session.get("courtroom_name").toString()
            : "";

        RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
        String caseName = randomStringGenerator.generateRandomString(10);
        String randomLogText = randomStringGenerator.generateRandomString(10);

    // Construct SOAP request
    return String.format(
    "    <s:Envelope\n"
    + "    <s:Header>\n"
    + "        <wsse:Security\n"
    + "            xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">\n"
    + "            <wsse:BinarySecurityToken QualificationValueType=\"http://schemas.emc.com/documentum#ResourceAccessToken\"\n"
    + "                xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" wsu:Id=\"RAD\">%s</wsse:BinarySecurityToken>\n"
    + "        </wsse:Security>\n"
    + "    </s:Header>\n"
    + "    <s:Body xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n"
    + "        <addLogEntry xmlns=\"http://com.synapps.mojdarts.service.com\">\n"
    + "            <document xmlns=\"\"><![CDATA[<log_entry Y=\"2023\" M=\"12\" D=\"5\" H=\"17\" MIN=\"29\" S=\"15\">\n"
    + "                <courthouse>%s</courthouse>\n"
    + "                <courtroom>%s</courtroom>\n"
    + "                <case_numbers>\n"
    + "                <case_number>Perf_%s</case_number>\n"
    + "                </case_numbers>\n"
    + "                <text>System : Perf_%s</text>\n"
    + "            </log_entry>]]></document>\n"
    + "        </addLogEntry>\n"
    + "    </s:Body>\n"
    + "</s:Envelope>",
        registrationToken, courtHouseName, courtRoom, caseName, randomLogText);
    } 

    public static String registerWithUsernameRequest(Session session, String userName, String password) {
    // Construct SOAP request
    return String.format(
        "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">\n"
        + "   <S:Header>\n"
        + "      <ServiceContext token=\"temporary/127.0.0.1-1700061962100--7690714146928305881\" xmlns=\"http://context.core.datamodel.fs.documentum.emc.com/\" xmlns:ns2=\"http://properties.core.datamodel.fs.documentum.emc.com/\" xmlns:ns3=\"http://profiles.core.datamodel.fs.documentum.emc.com/\" xmlns:ns4=\"http://query.core.datamodel.fs.documentum.emc.com/\" xmlns:ns5=\"http://content.core.datamodel.fs.documentum.emc.com/\" xmlns:ns6=\"http://core.datamodel.fs.documentum.emc.com/\">\n"
        + "         <Identities password=\"%s\" repositoryName=\"moj_darts\" userName=\"%s\" xsi:type=\"RepositoryIdentity\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"></Identities>\n"
        + "         <Profiles allowAsyncContentTransfer=\"false\" allowCachedContentTransfer=\"false\" isProcessOLELinks=\"false\" transferMode=\"MTOM\" xsi:type=\"ns3:ContentTransferProfile\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"></Profiles>\n"
        + "      </ServiceContext>\n"
        + "   </S:Header>\n"
        + "   <S:Body>\n"
        + "      <ns8:register xmlns:ns8=\"http://services.rt.fs.documentum.emc.com/\" xmlns:ns7=\"http://core.datamodel.fs.documentum.emc.com/\" xmlns:ns6=\"http://content.core.datamodel.fs.documentum.emc.com/\" xmlns:ns5=\"http://query.core.datamodel.fs.documentum.emc.com/\" xmlns:ns4=\"http://profiles.core.datamodel.fs.documentum.emc.com/\" xmlns:ns3=\"http://properties.core.datamodel.fs.documentum.emc.com/\" xmlns:ns2=\"http://context.core.datamodel.fs.documentum.emc.com/\">\n"
        + "         <context>\n"
        + "            <ns2:Identities xsi:type=\"ns2:RepositoryIdentity\" repositoryName=\"moj_darts\" password=\"%s\" userName=\"%s\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"></ns2:Identities>\n"
        + "            <ns2:Profiles xsi:type=\"ns4:ContentTransferProfile\" isProcessOLELinks=\"false\" allowAsyncContentTransfer=\"false\" allowCachedContentTransfer=\"false\" transferMode=\"MTOM\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"></ns2:Profiles>\n"
        + "         </context>\n"
        + "         <host>http://localhost:8070/service/darts/</host>\n"
        + "      </ns8:register>\n"
        + "   </S:Body>\n"
        + "</S:Envelope>",
        password, userName, password, userName);
    }
    public static String registerWithTokenRequest(Session session, String userName, String password) {

        String registrationToken = session.get("registrationToken");

        // Construct SOAP request
        return String.format(
            "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">\n"
            + "    <S:Header>\n"
            + "        <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">\n"
            + "            <wsse:BinarySecurityToken QualificationValueType=\"http://schemas.emc.com/documentum#ResourceAccessToken\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" wsu:Id=\"RAD\">%s</wsse:BinarySecurityToken>\n"
            + "        </wsse:Security>\n"
            + "    </S:Header>\n"
            + "    <S:Body>\n"
            + "        <ns8:register xmlns:ns8=\"http://services.rt.fs.documentum.emc.com/\" xmlns:ns7=\"http://core.datamodel.fs.documentum.emc.com/\" xmlns:ns6=\"http://content.core.datamodel.fs.documentum.emc.com/\" xmlns:ns5=\"http://query.core.datamodel.fs.documentum.emc.com/\" xmlns:ns4=\"http://profiles.core.datamodel.fs.documentum.emc.com/\" xmlns:ns3=\"http://properties.core.datamodel.fs.documentum.emc.com/\" xmlns:ns2=\"http://context.core.datamodel.fs.documentum.emc.com/\">\n"
            + "            <context>\n"
            + "                <ns2:Identities xsi:type=\"ns2:RepositoryIdentity\" repositoryName=\"moj_darts\" password=\"%s\" userName=\"%s\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"></ns2:Identities>\n"
            + "                <ns2:Profiles xsi:type=\"ns4:ContentTransferProfile\" isProcessOLELinks=\"false\" allowAsyncContentTransfer=\"false\" allowCachedContentTransfer=\"false\" transferMode=\"MTOM\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"></ns2:Profiles>\n"
            + "            </context>\n"
            + "            <host>http://localhost:8070/service/darts/</host>\n"
            + "        </ns8:register>\n"
            + "    </S:Body>\n"
            + "</S:Envelope>",
            registrationToken, password, userName);
    }
}