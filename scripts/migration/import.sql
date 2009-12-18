insert deeds (id,section,lot,lastname1,firstname1,middleInitial1,
				lastname2,firstname2,middleInitial2,issueDate,notes,lot2,cemetery_id)
select r.ID,r.SEC,r.LOT,r.LNAME1,r.FNAME1,r.MI1,
		r.LNAME2,r.FNAME2,r.MI2,r.DATE_ISSUE,r.NOTES,r.lot2,c.id
from rosehill.DEED r
left join cemeteries c on r.whiteoak=substr(c.name,1,1);
