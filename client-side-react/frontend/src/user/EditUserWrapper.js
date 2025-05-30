
import { useParams } from "react-router-dom";
import EditUser from "././EditUser";

function EditUserWrapper() {
  const { id } = useParams();
  return <EditUser userId={id} />;
}

export default EditUserWrapper;
