insert cemeteries set id=1,name='White Oak';
insert cemeteries set id=2,name='Rose Hill';



insert deeds (id,section,lot,lastname1,firstname1,middleInitial1,
				lastname2,firstname2,middleInitial2,issueDate,notes,lot2,cemetery_id)
select r.ID,r.SEC,r.LOT,r.LNAME1,r.FNAME1,r.MI1,
		r.LNAME2,r.FNAME2,r.MI2,r.DATE_ISSUE,r.NOTES,r.lot2,c.id
from rosehill.DEED r
left join cemeteries c on r.whiteoak=substr(c.name,1,1);


update rosehill.ROSEHILL set WHITEOAK='R' where WHITEOAK is null;

insert interments(id,section,lot,book,pageNumber,deceasedDate,
					lastname,firstname,middleInitial,
					birthPlace,lastResidence,age,sex,cemetery_id,notes,lot2)
select r.ID,r.SEC,r.LOT,r.BOOK,r.PAGENUM,r.DEATH,
		r.LNAME,r.FNAME,r.MI,
		r.POB,r.LATE_RES,r.AGE,r.SEX,c.id,r.NOTES,r.lot2
from rosehill.ROSEHILL r
left join cemeteries c on r.whiteoak=substr(c.name,1,1);


-- Migrate the interment sections into a section_id foreign key'd to a sections table
-- A little bit of cleanup on the data
update interments set section='P.G.' where section='P.G';
update interments set section=null where section='0';

insert sections (code,cemetery_id)
select distinct section,1 from interments
where cemetery_id=1 and section is not null;

insert sections (code,cemetery_id)
select distinct section,2 from interments
where cemetery_id=2 and section is not null;


alter table interments add section_id int unsigned after section;
alter table interments add foreign key (section_id) references sections(id);

update interments,sections
set section_id=sections.id
where interments.section=sections.code
and interments.cemetery_id=sections.cemetery_id;

alter table interments drop section;

-- Migrate the deeds sections into a section_id foreign key'd to a sections table
update deeds set section='A' where section='A MA';
update deeds set section='R.H.' where section='R.H';
update deeds set cemetery_id=2 where section='R.H.';


-- R.H. is not a section.  It was the way people were marking the Cemetery to be RoseHill
-- In these cases, the lot field has the actual section code
update interments set section_id=33,lot=null where section_id=39;
delete from sections where id=39;
-- A case where people put the lot number in the section field
update interments set section_id=34,lot=49,cemetery_id=2 where section_id=45;
delete from sections where id=45;
