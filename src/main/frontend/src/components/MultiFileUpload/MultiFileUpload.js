import axios from "axios";

function MultiFileUpload() {
  const api = process.env.REACT_APP_UPLOAD_API;
  const handleChange = (e) => {
    const formData = new FormData();
    const files = e.target.files;
    Array.from(files).forEach((file, index) => {
      formData.append(`files`, file);
    });
    axios.post(`${api}/multiple`, formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
  };

  return (
    <div className="App">
      <form>
        <input onChange={handleChange} multiple type="file" name="files" />
      </form>
    </div>
  );
}

export default MultiFileUpload;