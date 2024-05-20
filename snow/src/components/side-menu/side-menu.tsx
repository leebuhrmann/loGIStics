import { useRecoilValue } from "recoil";
import SideCreationView from "./side-form-view/side-creation-view";
import SideEditView from "./side-form-view/side-edit-view";
import SideInfoView from "./side-info-view/side-info-view";
import { viewStateAtom } from "@/state/atoms";

/**
 * Component that displays the common elements within SideMenu.
 * @param props - The props needed for the component.
 * @returns html elements for the SideMenu
 */
export default function SideMenu({ children }: any) {
  const viewState = useRecoilValue(viewStateAtom);

  return (
    <div className="side-menu w-full flex">
      {children}
      {viewState === "create" ? (
        <SideCreationView />
      ) : viewState === "edit" ? (
        <SideEditView />
      ) : (
        <SideInfoView />
      )}
    </div>
  );
}
