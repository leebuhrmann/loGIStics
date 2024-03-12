import SideInfoView from "./side-info-view/side-info-view";
import SideFormView from "@/components/side-menu/side-form-view/side-form-view-common";

import React from "react";

const SideMenu = ({ children }) => {
  return <div className="side-menu w-full">{children}</div>;
};

export default SideMenu;
