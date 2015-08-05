package us.kcadventuro.theriverdata

import org.jsoup.Jsoup;

def espnTop300URL = "http://espn.go.com/fantasy/football/story/_/id/12866396/top-300-rankings-2015";
def document = Jsoup.connect(espnTop300URL).get();
def top300Table = document.select("aside.inline-table").eq(1);
def rows = top300Table.select("tr");

def overallPlayerRankId;
def name;
def position;
def team;
def byeWeek;
def positionRank;
def csv = StringBuilder.newInstance();
def delimiter = ",";

def setOverallPlayerRankId( column ) {
	overallPlayerRankId = column.findAll( /\d+/ )*.toInteger()[0];
}

def getOverallPlayerRankId() {
	return overallPlayerRankId;
}

def setName( column ) {
	name = column.find( /[^.] | [^,]*/).toString().trim();
}

def getName() {
	return name;
}

def setPosition (column ) {
	position = column.find(/[^,.]*$/).toString().trim();
}

def getPosition() {
	return position;
}

def setTeam( column ) {
	team = column.toString().trim();
}

def getTeam() {
	return team;
}

def setByeWeek( column ) {
	
	if(column.find( /[-]/ )) {
		byeWeek = "0";
	} else {
		byeWeek = column.toString().trim();
	}
}

def getByeWeek() {
	return byeWeek;
}

def setPositionRank( column ) {
	positionRank = column.findAll( /\d+/ )*.toInteger()[0];
}

def getPositionRank() {
	return positionRank;
}

def csvBuilder = {
	input -> 
		csv << input;
} 


def processRow( row ) {
	def firstColumn = row.select("td:eq(0)").text();
	def secondColumn = row.select("td:eq(1)").text();
	def thridColumn = row.select("td:eq(2)").text();
	def fourthColumn = row.select("td:eq(3)").text();

	setOverallPlayerRankId( firstColumn );
	setName( firstColumn );
	setPosition( firstColumn );
	setTeam( secondColumn );
	setByeWeek( thridColumn );
	setPositionRank( fourthColumn );

}

rows.each { row ->
	processRow( row );
	if( getOverallPlayerRankId() != null && getOverallPlayerRankId() != "" ) {
		def value = getOverallPlayerRankId() + delimiter + getName() + delimiter + getPosition() + delimiter + getTeam() + delimiter + getByeWeek() + delimiter + getPositionRank() + System.getProperty("line.separator");
		csvBuilder( value );
	}
}

new File("top300.csv").write( csv.toString() );

