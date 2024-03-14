import SideInfoView from "./side-info-view/side-info-view";

export default function SideMenu({ subCheckValue, setSubCheckValue }) {
  return (
    <SideInfoView
      subCheckValue={subCheckValue}
      setSubCheckValue={setSubCheckValue}
    ></SideInfoView>
  );
}
