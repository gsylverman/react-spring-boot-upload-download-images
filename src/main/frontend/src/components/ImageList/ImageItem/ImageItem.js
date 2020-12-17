import axios from "axios";

function ImageItem({ name, fullName, url, setLoading }) {
  const deleteImage = () => {
    const api = process.env.REACT_APP_UPLOAD_API;
    setLoading(true);
    axios
      .post(`${api}/deleteFile/${fullName}`)
      .then((response) => {
        console.log(response);
        setLoading(false);
      })
      .catch((err) => console.log(err));
  };

  return (
    <>
      <div>Image name: {name}</div>
      <div>
        <img
          src={url}
          alt={name}
          style={{ maxHeight: "200px", width: "auto" }}
        />
      </div>
      <button onClick={deleteImage}>Delete Image</button>
    </>
  );
}

export default ImageItem;
