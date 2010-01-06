/**
 * @copyright 2010 City of Bloomington, Indiana
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 */
var COB = {};

COB.populateSections = function(cemetery_id,select_id,BASE_URL) {
	var url = BASE_URL + '/cemeteries/viewCemetery.php?format=json;cemetery_id=' + cemetery_id;

	YAHOO.util.Connect.asyncRequest('GET',url,{
		success : function (o) {
			var select = document.getElementById(select_id);
			select.innerHTML = '';
			select.appendChild(document.createElement('option'));

			var sections = YAHOO.lang.JSON.parse(o.responseText).sections
			for (i in sections) {
				var option = document.createElement('option');
				option.setAttribute('value',sections[i].id);
				option.appendChild(document.createTextNode(sections[i].code));
				select.appendChild(option);
			}
		},

		failure : function (o) {
		}
	});
}
