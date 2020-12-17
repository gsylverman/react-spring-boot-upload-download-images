import axios from "axios";

function MultiFileUpload({ setLoading }) {
  const api = process.env.REACT_APP_UPLOAD_API;
  const handleChange = (e) => {
    const formData = new FormData();
    const files = e.target.files;
    Array.from(files).forEach((file, index) => {
      formData.append(`files`, file);
    });
    setLoading(true);
    axios
      .post(`${api}/multiple`, formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      })
      .then((respose) => {
        console.log(respose);
        setLoading(false);
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
