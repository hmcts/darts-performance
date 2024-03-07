package simulations.Scripts.SOAPRequestBuilder;

import simulations.Scripts.Utilities.Feeders;
import simulations.Scripts.Utilities.NumberGenerator;
import simulations.Scripts.Utilities.RandomStringGenerator;
import simulations.Scripts.Utilities.TimestampGenerator;

import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;

import io.gatling.javaapi.core.Session;

public class SOAPRequestBuilder {

    private static final String USERNAME = EnvironmentURL.DARTS_EXTERNAL_SOAP_USERNAME.getUrl();
    private static final String PASSWORD = EnvironmentURL.DARTS_EXTERNAL_SOAP_PASSWORD.getUrl();

    public static String AddDcoumentSOAPRequest(Session session) {
        // Retrieve values from session or define defaults if needed
        String courtHouseType = session.get("CourtHouseType").toString();
        String courtHouseName = session.get("CourtHouseName").toString();    
        String courtHouseCode = session.get("CourtHouseCode").toString();    
        String eventType = Feeders.getRandomEventCode(); 

        // Generate dynamic values
        NumberGenerator generator = new NumberGenerator(10);
        RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
        String documentName = randomStringGenerator.generateRandomString(10);
        String getCurrentTimestamp = TimestampGenerator.getCurrentTimestamp();

        // Construct SOAP request
        return String.format("<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <s:Header>\n" +
                "    <ServiceContext token=\"temporary/127.0.0.1-1694086218480-789961425\" xmlns=\"http://context.core.datamodel.fs.documentum.emc.com/\">\n" +
                "      <Identities xsi:type=\"RepositoryIdentity\" userName=\"" + USERNAME + "\" password=\"" + PASSWORD + "\" repositoryName=\"moj_darts\" domain=\"\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>\n" +
                "      <RuntimeProperties/>\n" +
                "    </ServiceContext>\n" +
                "  </s:Header>\n" +
                "  <s:Body>\n" +
                "    <ns5:addDocument xmlns:ns5=\"http://com.synapps.mojdarts.service.com\">\n" +
                "      <messageId>18418</messageId>\n" +
                "      <type>%s</type>\n" +
                "      <subType>" + eventType + "</subType>\n" +
                "      <document><![CDATA[<cs:DailyList xmlns:cs=\"http://www.courtservice.gov.uk/schemas/courtservice\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.courtservice.gov.uk/schemas/courtservice DailyList-v5-2.xsd\" xmlns:apd=\"http://www.govtalk.gov.uk/people/AddressAndPersonalDetails\"><cs:DocumentID><cs:DocumentName>Perf_Doc_" + documentName + "</cs:DocumentName><cs:UniqueID>Perf_" + generator.generateNextNumber() + "</cs:UniqueID><cs:DocumentType>" + eventType + "</cs:DocumentType><cs:TimeStamp>" + getCurrentTimestamp + "</cs:TimeStamp><cs:Version>1.0</cs:Version><cs:SecurityClassification>NPM</cs:SecurityClassification><cs:SellByDate>2010-12-15</cs:SellByDate><cs:XSLstylesheetURL>http://www.courtservice.gov.uk/transforms/courtservice/dailyListHtml.xsl</cs:XSLstylesheetURL></cs:DocumentID><cs:ListHeader><cs:ListCategory>Criminal</cs:ListCategory><cs:StartDate>2010-02-18</cs:StartDate><cs:EndDate>2010-02-18</cs:EndDate><cs:Version>FINAL v1</cs:Version><cs:CRESTprintRef>MCD/112585</cs:CRESTprintRef><cs:PublishedTime>2010-02-17T16:16:50</cs:PublishedTime><cs:CRESTlistID>12298</cs:CRESTlistID></cs:ListHeader><cs:CrownCourt><cs:CourtHouseType>" + courtHouseType + "</cs:CourtHouseType><cs:CourtHouseCode CourtHouseShortName=\"SNARE\">" + courtHouseCode + "</cs:CourtHouseCode><cs:CourtHouseName>" + courtHouseName + "</cs:CourtHouseName><cs:CourtHouseAddress><apd:Line>THE CROWN COURT AT " + courtHouseName + "</apd:Line><apd:Line>75 HOLLYBUSH HILL</apd:Line><apd:Line>" + courtHouseName + ", LONDON</apd:Line><apd:PostCode>E11 1QW</apd:PostCode></cs:CourtHouseAddress><cs:CourtHouseDX>DX 98240 WANSTEAD 2</cs:CourtHouseDX><cs:CourtHouseTelephone>02085300000</cs:CourtHouseTelephone><cs:CourtHouseFax>02085300072</cs:CourtHouseFax></cs:CrownCourt><cs:CourtLists><cs:CourtList><cs:CourtHouse><cs:CourtHouseType>" + courtHouseType + "</cs:CourtHouseType><cs:CourtHouseCode>" + courtHouseCode + "</cs:CourtHouseCode><cs:CourtHouseName>" + courtHouseName + "</cs:CourtHouseName></cs:CourtHouse><cs:Sittings><cs:Sitting><cs:CourtRoomNumber>1AB</cs:CourtRoomNumber><cs:SittingSequenceNo>1</cs:SittingSequenceNo><cs:SittingAt>10:00:00</cs:SittingAt><cs:SittingPriority>T</cs:SittingPriority><cs:Judiciary><cs:Judge><apd:CitizenNameSurname>N&#47;A</apd:CitizenNameSurname><apd:CitizenNameRequestedName>N&#47;A</apd:CitizenNameRequestedName><cs:CRESTjudgeID>0</cs:CRESTjudgeID></cs:Judge></cs:Judiciary><cs:Hearings><cs:Hearing><cs:HearingSequenceNumber>1</cs:HearingSequenceNumber><cs:HearingDetails HearingType=\"TRL\"><cs:HearingDescription>For Trial</cs:HearingDescription><cs:HearingDate>2010-02-18</cs:HearingDate></cs:HearingDetails><cs:CRESThearingID>1</cs:CRESThearingID><cs:TimeMarkingNote>10:00 AM</cs:TimeMarkingNote><cs:CaseNumber>T20107001</cs:CaseNumber><cs:Prosecution ProsecutingAuthority=\"Crown Prosecution Service\"><cs:ProsecutingReference>CPS</cs:ProsecutingReference><cs:ProsecutingOrganisation><cs:OrganisationName>Crown Prosecution Service</cs:OrganisationName></cs:ProsecutingOrganisation></cs:Prosecution><cs:CommittingCourt><cs:CourtHouseType>" + courtHouseType + "</cs:CourtHouseType><cs:CourtHouseCode CourtHouseShortName=\"BAM\">2725</cs:CourtHouseCode><cs:CourtHouseName>" + courtHouseName + "</cs:CourtHouseName><cs:CourtHouseAddress><apd:Line>7C HIGH STREET</apd:Line><apd:Line>-</apd:Line><apd:Line>BARNET</apd:Line><apd:PostCode>EN5 5UE</apd:PostCode></cs:CourtHouseAddress><cs:CourtHouseDX>DX 8626 BARNET</cs:CourtHouseDX><cs:CourtHouseTelephone>02084419042</cs:CourtHouseTelephone></cs:CommittingCourt><cs:Defendants><cs:Defendant><cs:PersonalDetails><cs:Name><apd:CitizenNameForename>Franz</apd:CitizenNameForename><apd:CitizenNameSurname>KAFKA</apd:CitizenNameSurname></cs:Name><cs:IsMasked>no</cs:IsMasked><cs:DateOfBirth><apd:BirthDate>1962-06-12</apd:BirthDate><apd:VerifiedBy>not verified</apd:VerifiedBy></cs:DateOfBirth><cs:Sex>male</cs:Sex><cs:Address><apd:Line>ADDRESS LINE 1</apd:Line><apd:Line>ADDRESS LINE 2</apd:Line><apd:Line>ADDRESS LINE 3</apd:Line><apd:Line>ADDRESS LINE 4</apd:Line><apd:Line>SOMETOWN, SOMECOUNTY</apd:Line><apd:PostCode>GU12 7RT</apd:PostCode></cs:Address></cs:PersonalDetails><cs:ASNs><cs:ASN>0723XH1000000262665K</cs:ASN></cs:ASNs><cs:CRESTdefendantID>29161</cs:CRESTdefendantID><cs:PNCnumber>20123456789L</cs:PNCnumber><cs:URN>62AA1010646</cs:URN><cs:CustodyStatus>In custody</cs:CustodyStatus></cs:Defendant></cs:Defendants></cs:Hearing></cs:Hearings></cs:Sitting></cs:Sittings></cs:CourtList></cs:CourtLists></cs:DailyList>]]></document>\n" +
                "    </ns5:addDocument>\n" +
                "  </s:Body>\n" +
                "</s:Envelope>",
                eventType);
    }

    public static String AddDcoumentSOAPTokenRequest(Session session) {

        String registrationToken = session.get("registrationToken");

        // Retrieve values from session or define defaults if needed
        String courtHouseType = session.get("CourtHouseType").toString();
        String courtHouseName = session.get("CourtHouseName").toString();    
        String courtHouseCode = session.get("CourtHouseCode").toString();    
        String eventType = Feeders.getRandomEventCode(); 

        // Generate dynamic values
        NumberGenerator generator = new NumberGenerator(10);
        RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
        String documentName = randomStringGenerator.generateRandomString(10);
        String getCurrentTimestamp = TimestampGenerator.getCurrentTimestamp();

        // Construct SOAP request
        return String.format("<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
        "   <s:Header>\n" +
        "      <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">\n" +
        "         <wsse:BinarySecurityToken QualificationValueType=\"http://schemas.emc.com/documentum#ResourceAccessToken\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" wsu:Id=\"RAD\">%s</wsse:BinarySecurityToken>\n" +
        "      </wsse:Security>\n" +
        "   </s:Header>\n" +
        "   <s:Body>\n" +
        "      <ns5:addDocument xmlns:ns5=\"http://com.synapps.mojdarts.service.com\">\n" +
        "         <messageId>18418</messageId>\n" +
        "         <type>" + eventType + "</type>\n" +
        "         <subType>" + eventType + "</subType>\n" +
        "         <document><![CDATA[<cs:DailyList xmlns:cs=\"http://www.courtservice.gov.uk/schemas/courtservice\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.courtservice.gov.uk/schemas/courtservice DailyList-v5-2.xsd\" xmlns:apd=\"http://www.govtalk.gov.uk/people/AddressAndPersonalDetails\"><cs:DocumentID><cs:DocumentName>Perf_Doc_" + documentName + "</cs:DocumentName><cs:UniqueID>Perf_" + generator.generateNextNumber() + "</cs:UniqueID><cs:DocumentType>" + eventType + "</cs:DocumentType><cs:TimeStamp>" + getCurrentTimestamp + "</cs:TimeStamp><cs:Version>1.0</cs:Version><cs:SecurityClassification>NPM</cs:SecurityClassification><cs:SellByDate>2010-12-15</cs:SellByDate><cs:XSLstylesheetURL>http://www.courtservice.gov.uk/transforms/courtservice/dailyListHtml.xsl</cs:XSLstylesheetURL></cs:DocumentID><cs:ListHeader><cs:ListCategory>Criminal</cs:ListCategory><cs:StartDate>2010-02-18</cs:StartDate><cs:EndDate>2010-02-18</cs:EndDate><cs:Version>FINAL v1</cs:Version><cs:CRESTprintRef>MCD/112585</cs:CRESTprintRef><cs:PublishedTime>2010-02-17T16:16:50</cs:PublishedTime><cs:CRESTlistID>12298</cs:CRESTlistID></cs:ListHeader><cs:CrownCourt><cs:CourtHouseType>" + courtHouseType + "</cs:CourtHouseType><cs:CourtHouseCode CourtHouseShortName=\"SNARE\">" + courtHouseCode + "</cs:CourtHouseCode><cs:CourtHouseName>" + courtHouseName + "</cs:CourtHouseName><cs:CourtHouseAddress><apd:Line>THE CROWN COURT AT Bristol</apd:Line><apd:Line>75 HOLLYBUSH HILL</apd:Line><apd:Line>Bristol, LONDON</apd:Line><apd:PostCode>E11 1QW</apd:PostCode></cs:CourtHouseAddress><cs:CourtHouseDX>DX 98240 WANSTEAD 2</cs:CourtHouseDX><cs:CourtHouseTelephone>02085300000</cs:CourtHouseTelephone><cs:CourtHouseFax>02085300072</cs:CourtHouseFax></cs:CrownCourt><cs:CourtLists><cs:CourtList><cs:CourtHouse><cs:CourtHouseType>" + courtHouseType + "</cs:CourtHouseType><cs:CourtHouseCode>" + courtHouseCode + "</cs:CourtHouseCode><cs:CourtHouseName>" + courtHouseName + "</cs:CourtHouseName></cs:CourtHouse><cs:Sittings><cs:Sitting><cs:CourtRoomNumber>1</cs:CourtRoomNumber><cs:SittingSequenceNo>1</cs:SittingSequenceNo><cs:SittingAt>10:00:00</cs:SittingAt><cs:SittingPriority>T</cs:SittingPriority><cs:Judiciary><cs:Judge><apd:CitizenNameSurname>N/A</apd:CitizenNameSurname><apd:CitizenNameRequestedName>N/A</apd:CitizenNameRequestedName><cs:CRESTjudgeID>0</cs:CRESTjudgeID></cs:Judge></cs:Judiciary><cs:Hearings><cs:Hearing><cs:HearingSequenceNumber>1</cs:HearingSequenceNumber><cs:HearingDetails HearingType=\"TRL\"><cs:HearingDescription>For Trial</cs:HearingDescription><cs:HearingDate>2010-02-18</cs:HearingDate></cs:HearingDetails><cs:CRESThearingID>1</cs:CRESThearingID><cs:TimeMarkingNote>10:00 AM</cs:TimeMarkingNote><cs:CaseNumber>T20107001</cs:CaseNumber><cs:Prosecution ProsecutingAuthority=\"Crown Prosecution Service\"><cs:ProsecutingReference>CPS</cs:ProsecutingReference><cs:ProsecutingOrganisation><cs:OrganisationName>Crown Prosecution Service</cs:OrganisationName></cs:ProsecutingOrganisation></cs:Prosecution><cs:CommittingCourt><cs:CourtHouseType>" + courtHouseType + "</cs:CourtHouseType><cs:CourtHouseCode CourtHouseShortName=\"BAM\">" + courtHouseCode + "</cs:CourtHouseCode><cs:CourtHouseName>" + courtHouseName + "</cs:CourtHouseName><cs:CourtHouseAddress><apd:Line>7C HIGH STREET</apd:Line><apd:Line>-</apd:Line><apd:Line>BARNET</apd:Line><apd:PostCode>EN5 5UE</apd:PostCode></cs:CourtHouseAddress><cs:CourtHouseDX>DX 8626 BARNET</cs:CourtHouseDX><cs:CourtHouseTelephone>02084419042</cs:CourtHouseTelephone></cs:CommittingCourt><cs:Defendants><cs:Defendant><cs:PersonalDetails><cs:Name><apd:CitizenNameForename>Franz</apd:CitizenNameForename><apd:CitizenNameSurname>KAFKA</apd:CitizenNameSurname></cs:Name><cs:IsMasked>no</cs:IsMasked><cs:DateOfBirth><apd:BirthDate>1962-06-12</apd:BirthDate><apd:VerifiedBy>not verified</apd:VerifiedBy></cs:DateOfBirth><cs:Sex>male</cs:Sex><cs:Address><apd:Line>ADDRESS LINE 1</apd:Line><apd:Line>ADDRESS LINE 2</apd:Line><apd:Line>ADDRESS LINE 3</apd:Line><apd:Line>ADDRESS LINE 4</apd:Line><apd:Line>SOMETOWN, SOMECOUNTY</apd:Line><apd:PostCode>GU12 7RT</apd:PostCode></cs:Address></cs:PersonalDetails><cs:ASNs><cs:ASN>0723XH1000000262665K</cs:ASN></cs:ASNs><cs:CRESTdefendantID>29161</cs:CRESTdefendantID><cs:PNCnumber>20123456789L</cs:PNCnumber><cs:URN>62AA1010646</cs:URN><cs:CustodyStatus>In custody</cs:CustodyStatus></cs:Defendant></cs:Defendants></cs:Hearing></cs:Hearings></cs:Sitting></cs:Sittings></cs:CourtList></cs:CourtLists></cs:DailyList>]]></document>\n" +
        "      </ns5:addDocument>\n" +
        "   </s:Body>\n" +
        "</s:Envelope>",
        registrationToken, courtHouseType, courtHouseName, courtHouseCode);        
    }

    public static String GetCasesSOAPRequest(Session session) {
        // Retrieve values from session or define defaults if needed
        String courtHouseName = session.get("CourtHouseName").toString();    
        String courtRoom = session.get("CourtRoom").toString();    

    // Construct SOAP request
    return String.format(
        "<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
        "   <s:Header>\n" +
        "      <ServiceContext token=\"temporary/127.0.0.1-1694086218480-789961425\" xmlns=\"http://context.core.datamodel.fs.documentum.emc.com/\">\n" +
        "         <Identities xsi:type=\"RepositoryIdentity\" userName=\"%s\" password=\"%s\" repositoryName=\"moj_darts\" domain=\"\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>\n" +
        "         <RuntimeProperties/>\n" +
        "      </ServiceContext>\n" +
        "   </s:Header>\n" +
        "   <s:Body xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" +
        "      <ns2:getCases xmlns:ns2=\"http://com.synapps.mojdarts.service.com\">\n" +
        "         <courthouse>%s</courthouse>\n" +
        "         <courtroom>%s</courtroom>\n" +
        "         <date>2023-11-29</date>\n" +
        "      </ns2:getCases>\n" +
        "   </s:Body>\n" +
        "</s:Envelope>",
        USERNAME, PASSWORD, courtHouseName, courtRoom);  
    }
  

    public static String AddCaseSOAPRequest(Session session) {
        // Retrieve values from session or define defaults if needed
        String courtHouseName = session.get("CourtHouseName").toString();    
        String courtRoom = session.get("CourtRoom").toString();    

        // Generate dynamic values
        RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
        String defendantName = randomStringGenerator.generateRandomString(10);
        String defendantName2 = randomStringGenerator.generateRandomString(10);
        String judgeName = randomStringGenerator.generateRandomString(10);

    // Construct SOAP request
    return String.format(
        "<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
        "  <s:Header>\n" +
        "    <ServiceContext token=\"temporary/127.0.0.1-1694086218480-789961425\" xmlns=\"http://context.core.datamodel.fs.documentum.emc.com/\">\n" +
        "      <Identities xsi:type=\"RepositoryIdentity\" userName=\"%s\" password=\"%s\" repositoryName=\"moj_darts\" domain=\"\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>\n" +
        "      <RuntimeProperties/>\n" +
        "    </ServiceContext>\n" +
        "  </s:Header>\n" +
        "  <s:Body xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" +
        "    <ns2:addCase xmlns:ns2=\"http://com.synapps.mojdarts.service.com\">\n" +
        "      <document><![CDATA[<case type=\"1\" id=\"U20231129-1733\"><courthouse>%s</courthouse><courtroom>%s</courtroom><defendants><defendant>%s</defendant><defendant>%s</defendant></defendants><judges><judge>Mr Judge</judge><judge>Mrs %s</judge></judges><prosecutors><prosecutor>Mr Prosecutor</prosecutor><prosecutor>Mrs Prosecutor</prosecutor></prosecutors></case>]]></document>\n" +
        "    </ns2:addCase>\n" +
        "  </s:Body>\n" +
        "</s:Envelope>",
        USERNAME, PASSWORD, courtHouseName, courtRoom, defendantName, defendantName2, judgeName);
    }

    public static String AddAudioSOAPRequest(Session session) {
            // Retrieve values from session or define defaults if needed
            String courtHouseName = session.get("CourtHouseName").toString();    
            String courtRoom = session.get("CourtRoom").toString(); 
            RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
            String caseName = randomStringGenerator.generateRandomString(10);

    // Construct SOAP request
    return String.format(
        "<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:com=\"http://com.synapps.mojdarts.service.com\" xmlns:core=\"http://core.datamodel.fs.documentum.emc.com/\" xmlns:prop=\"http://properties.core.datamodel.fs.documentum.emc.com/\" xmlns:con=\"http://content.core.datamodel.fs.documentum.emc.com/\">\n" +
        "   <s:Header>\n" +
        "      <ServiceContext token=\"temporary/127.0.0.1-1694086218480-789961425\" xmlns=\"http://context.core.datamodel.fs.documentum.emc.com/\">\n" +
        "         <Identities xsi:type=\"RepositoryIdentity\" userName=\"%s\" password=\"%s\" repositoryName=\"moj_darts\" domain=\"\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>\n" +
        "         <RuntimeProperties/>\n" +
        "      </ServiceContext>\n" +
        "   </s:Header>\n" +
        "   <s:Body>\n" +
        "      <com:addAudio>\n" +
        "         <!--Optional:-->\n" +
        "         <document>&lt;audio&gt;&lt;start Y=\"2023\" M=\"9\" D=\"7\" H=\"11\" MIN=\"26\" S=\"51\" /&gt;&lt;end Y=\"2023\" M=\"9\" D=\"7\" H=\"11\" MIN=\"29\" S=\"49\" /&gt;&lt;channel&gt;1&lt;/channel&gt;&lt;max_channels&gt;4&lt;/max_channels&gt;&lt;mediaformat&gt;mpeg2&lt;/mediaformat&gt;&lt;mediafile&gt;0001.a00&lt;/mediafile&gt;&lt;courthouse&gt;%s&lt;/courthouse&gt;&lt;courtroom&gt;%s&lt;/courtroom&gt;&lt;case_numbers&gt;&lt;case_number&gt;%s&lt;/case_number&gt;&lt;case_number&gt;test&lt;/case_number&gt;&lt;/case_numbers&gt;&lt;/audio&gt;</document>\n" +
        "      </com:addAudio>\n" +
        "   </s:Body>\n" +
        "</s:Envelope>",
        USERNAME, PASSWORD, courtHouseName, courtRoom, caseName);
    }    

    public static String AddCourtLogSOAPRequest(Session session) {
        // Retrieve values from session or define defaults if needed
        String courtHouseName = session.get("CourtHouseName").toString();    
        String courtRoom = session.get("CourtRoom").toString(); 
        RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
        String caseName = randomStringGenerator.generateRandomString(10);

    // Construct SOAP request
    return String.format(
        "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:com=\"http://com.synapps.mojdarts.service.com\">\n" +
        "   <soapenv:Header>\n" +
        "      <ServiceContext token=\"temporary/127.0.0.1-1694086218480-789961425\" xmlns=\"http://context.core.datamodel.fs.documentum.emc.com/\">\n" +
        "         <Identities xsi:type=\"RepositoryIdentity\" userName=\"%s\" password=\"%s\" repositoryName=\"moj_darts\" domain=\"\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>\n" +
        "         <RuntimeProperties/>\n" +
        "      </ServiceContext>\n" +
        "   </soapenv:Header>\n" +
        "   <soapenv:Body>\n" +
        "      <addLogEntry xmlns=\"http://com.synapps.mojdarts.service.com\">\n" +
        "         <document xmlns=\"\">&lt;log_entry Y=&quot;2023&quot; M=&quot;01&quot; D=&quot;01&quot; H=&quot;10&quot; MIN=&quot;00&quot; S=&quot;00&quot;&gt;&lt;courthouse&gt;%s&lt;/courthouse&gt;&lt;courtroom&gt;%s&lt;/courtroom&gt;&lt;case_numbers&gt;&lt;case_number&gt;PerfCase_%s&lt;/case_number&gt;&lt;/case_numbers&gt;&lt;text&gt;THISISEVENTTEXT&lt;/text&gt;&lt;/log_entry&gt;\n" +
        "         </document>\n" +
        "      </addLogEntry>\n" +
        "   </soapenv:Body>\n" +
        "</soapenv:Envelope>",
        USERNAME, PASSWORD, courtHouseName, courtRoom, caseName);
    } 


    public static String RegisterWithUsernameSOAPRequest(Session session) {
    // Construct SOAP request
    return String.format(
        "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
        "   <S:Header>\n" +
        "      <ServiceContext token=\"temporary/127.0.0.1-1700061962100--7690714146928305881\" xmlns=\"http://context.core.datamodel.fs.documentum.emc.com/\" xmlns:ns2=\"http://properties.core.datamodel.fs.documentum.emc.com/\" xmlns:ns3=\"http://profiles.core.datamodel.fs.documentum.emc.com/\" xmlns:ns4=\"http://query.core.datamodel.fs.documentum.emc.com/\" xmlns:ns5=\"http://content.core.datamodel.fs.documentum.emc.com/\" xmlns:ns6=\"http://core.datamodel.fs.documentum.emc.com/\">\n" +
        "         <Identities password=\"%s\" repositoryName=\"moj_darts\" userName=\"%s\" xsi:type=\"RepositoryIdentity\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"></Identities>\n" +
        "         <Profiles allowAsyncContentTransfer=\"false\" allowCachedContentTransfer=\"false\" isProcessOLELinks=\"false\" transferMode=\"MTOM\" xsi:type=\"ns3:ContentTransferProfile\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"></Profiles>\n" +
        "      </ServiceContext>\n" +
        "   </S:Header>\n" +
        "   <S:Body>\n" +
        "      <ns8:register xmlns:ns8=\"http://services.rt.fs.documentum.emc.com/\" xmlns:ns7=\"http://core.datamodel.fs.documentum.emc.com/\" xmlns:ns6=\"http://content.core.datamodel.fs.documentum.emc.com/\" xmlns:ns5=\"http://query.core.datamodel.fs.documentum.emc.com/\" xmlns:ns4=\"http://profiles.core.datamodel.fs.documentum.emc.com/\" xmlns:ns3=\"http://properties.core.datamodel.fs.documentum.emc.com/\" xmlns:ns2=\"http://context.core.datamodel.fs.documentum.emc.com/\">\n" +
        "         <context>\n" +
        "            <ns2:Identities xsi:type=\"ns2:RepositoryIdentity\" repositoryName=\"moj_darts\" password=\"%s\" userName=\"%s\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"></ns2:Identities>\n" +
        "            <ns2:Profiles xsi:type=\"ns4:ContentTransferProfile\" isProcessOLELinks=\"false\" allowAsyncContentTransfer=\"false\" allowCachedContentTransfer=\"false\" transferMode=\"MTOM\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"></ns2:Profiles>\n" +
        "         </context>\n" +
        "         <host>http://localhost:8070/service/darts/</host>\n" +
        "      </ns8:register>\n" +
        "   </S:Body>\n" +
        "</S:Envelope>",
        PASSWORD, USERNAME, PASSWORD, USERNAME);
    }
    public static String RegisterWithTokenSOAPRequest(Session session) {

        String registrationToken = session.get("registrationToken");

        // Construct SOAP request
        return String.format(
            "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "    <S:Header>\n" +
            "        <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">\n" +
            "            <wsse:BinarySecurityToken QualificationValueType=\"http://schemas.emc.com/documentum#ResourceAccessToken\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" wsu:Id=\"RAD\">%s</wsse:BinarySecurityToken>\n" +
            "        </wsse:Security>\n" +
            "    </S:Header>\n" +
            "    <S:Body>\n" +
            "        <ns8:register xmlns:ns8=\"http://services.rt.fs.documentum.emc.com/\" xmlns:ns7=\"http://core.datamodel.fs.documentum.emc.com/\" xmlns:ns6=\"http://content.core.datamodel.fs.documentum.emc.com/\" xmlns:ns5=\"http://query.core.datamodel.fs.documentum.emc.com/\" xmlns:ns4=\"http://profiles.core.datamodel.fs.documentum.emc.com/\" xmlns:ns3=\"http://properties.core.datamodel.fs.documentum.emc.com/\" xmlns:ns2=\"http://context.core.datamodel.fs.documentum.emc.com/\">\n" +
            "            <context>\n" +
            "                <ns2:Identities xsi:type=\"ns2:RepositoryIdentity\" repositoryName=\"moj_darts\" password=\"%s\" userName=\"%s\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"></ns2:Identities>\n" +
            "                <ns2:Profiles xsi:type=\"ns4:ContentTransferProfile\" isProcessOLELinks=\"false\" allowAsyncContentTransfer=\"false\" allowCachedContentTransfer=\"false\" transferMode=\"MTOM\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"></ns2:Profiles>\n" +
            "            </context>\n" +
            "            <host>http://localhost:8070/service/darts/</host>\n" +
            "        </ns8:register>\n" +
            "    </S:Body>\n" +
            "</S:Envelope>",
            registrationToken, PASSWORD, USERNAME);
    }
}