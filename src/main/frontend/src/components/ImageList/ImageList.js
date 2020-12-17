import axios from "axios";
import { useCallback, useEffect, useState } from "react";
import ImageItem from "./ImageItem/ImageItem";

function ImageList({ loading, setLoading }) {
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
    <ImageItem key={item.id} {...item} setLoading={setLoading} />
  ));
}

export default ImageList;
