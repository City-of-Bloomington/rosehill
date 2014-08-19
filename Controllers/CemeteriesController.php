<?php
/**
 * @copyright 2014 City of Bloomington, Indiana
 * @license http://www.gnu.org/licenses/agpl.txt GNU/AGPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 */
namespace Application\Controllers;
use Application\Models\Cemetery;
use Application\Models\CemeteriesTable;
use Blossom\Classes\Controller;
use Blossom\Classes\Block;

class CemeteriesController extends Controller
{
    private function loadCemetery($id)
    {
        if (!empty($id)) {
            try {
                $cemetery = new Cemetery($id);
                return $cemetery;
            }
            catch (\Exception $e) {
                $_SESSION['errorMessages'][] = $e;
            }
        }
        else {
            $_SESSION['errorMessages'][] = new \Exception('cemeteries/unknownCemetery');
        }
        header('Location: '.BASE_URL.'/cemeteries');
        exit();
    }

    public function index()
    {
        $table = new CemeteriesTable();
        $list = $table->find();
        $this->template->blocks[] = new Block('cemeteries/list.inc', ['cemeteries'=>$list]);
    }

    public function view()
    {
        $cemetery = $this->loadCemetery($_GET['cemetery_id']);
        $this->template->blocks[] = new Block('cemeteries/info.inc', ['cemetery'=>$cemetery]);
    }

    public function update()
    {
        if (!empty($_REQUEST['cemetery_id'])) {
            $cemetery = $this->loadCemetery($_REQUEST['cemetery_id']);
        }
        else {
            $cemetery = new Cemetery();
        }

        if (isset($_POST['name'])) {
            $cemetery->handleUpdate($_POST);
            try {
                $cemetery->save();

                if (isset($_FILES)) {
                    if (isset($_FILES['map']) && $_FILES['map']['tmp_name']) {
                        $cemetery->saveMap($_FILES['map'],'full');
                    }
                    if (isset($_FILES['thumbnail']) && $_FILES['thumbnail']['tmp_name']) {
                        $section->saveMap($_FILES['thumbnail'],'thumbnail');
                    }
                }

                header('Location: '.BASE_URL.'/cemeteries/view?cemetery_id='.$cemetery->getId());
                exit();
            }
            catch (\Exception $e) {
                $_SESSION['errorMessages'][] = $e;
            }
        }

        $this->template->blocks[] = new Block('/cemeteries/updateForm.inc', ['cemetery'=>$cemetery]);
    }
}
