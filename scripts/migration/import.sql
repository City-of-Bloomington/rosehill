insert cemeteries set id=1,name='White Oak';
insert cemeteries set id=2,name='Rose Hill';


insert deeds (id,section,lot,lastname1,firstname1,middleInitial1,
				lastname2,firstname2,middleInitial2,issueDate,notes,lot2,cemetery_id)
select r.ID,r.SEC,r.LOT,r.LNAME1,r.FNAME1,r.MI1,
		r.LNAME2,r.FNAME2,r.MI2,r.DATE_ISSUE,r.NOTES,r.lot2,c.id
from rosehill.DEED r
left join cemeteries c on r.whiteoak=substr(c.name,1,1);


update rosehill.ROSEHILL set WHITEOAK='R' where WHITEOAK is null;

insert sections (code,cemetery_id)
select distinct SEC,1 from rosehill.ROSEHILL where WHITEOAK='W' and SEC is not null;

insert sections (code,cemetery_id)
select distinct SEC,2 from rosehill.ROSEHILL where WHITEOAK='R' and SEC is not null;


insert interments(id,section_id,lot,book,pageNumber,deceasedDate,
					lastname,firstname,middleInitial,
					birthPlace,lastResidence,age,sex,cemetery_id,notes,lot2)
select r.ID,s.id,r.LOT,r.BOOK,r.PAGENUM,r.DEATH,
		r.LNAME,r.FNAME,r.MI,
		r.POB,r.LATE_RES,r.AGE,r.SEX,c.id,r.NOTES,r.lot2
from rosehill.ROSEHILL r
left join cemeteries c on r.WHITEOAK=substr(c.name,1,1)
left join sections s on r.SEC=s.code and c.id=s.cemetery_id;
