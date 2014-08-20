"use strict";
/**
 * @copyright 2014 City of Bloomington, Indiana
 * @license http://www.gnu.org/licenses/agpl.txt GNU/AGPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 */
var COB = {
	populateSections: function (cemetery_id, select_id, BASE_URL) {
		var url = BASE_URL + '/cemeteries/view?format=json;cemetery_id=' + cemetery_id;

		YUI().use('io', 'json', function (Y) {
			Y.io(url, {
				on: {
					complete: function (id, o, args) {
						var select   = document.getElementById(select_id),
							sections = Y.JSON.parse(o.responseText).sections,
							i        = 0;

						select.innerHTML = '';
						select.appendChild(document.createElement('option'));

						for (i in sections) {
							var option = document.createElement('option');
							option.setAttribute('value',sections[i].id);
							option.appendChild(document.createTextNode(sections[i].code));
							select.appendChild(option);
						}
					}
				}
			});
		});
	},

	// A handy function for doing pop-up confirmations when deleting something
	deleteConfirmation: function (url) {
		if (confirm("Are you really sure you want to delete this?\n\nOnce deleted it will be gone forever.")) {
			document.location.href = url;
			return true;
		}
		else {
			return false;
		}
	}
};
