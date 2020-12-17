import axios from "axios";
import { useCallback, useEffect, useState } from "react";

function ImageList({ loading }) {
  const api = process.env.REACT_APP_UPLOAD_API;
  const [images, setImages] = useState([]);

  const fetchImages = useCallback(async () => {
    const response = await axios.get(api + "/images");
    console.log(response.data);
    setImages(response.data);
  }, [api]);

  useEffect(() => {
    fetchImages();
  }, [fetchImages, loading]);

  return images.map((item) => (
    <div key={item.id}>
      <div>Image name: {item.name}</div>
      <div>
        <img
          src={item.url}
          alt={item.name}
          style={{ maxHeight: "200px", width: "auto" }}
        />
      </div>
    </div>
  ));
}

export default ImageList;
