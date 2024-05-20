import { useEffect } from "react";
import { RecoilState, useRecoilValue } from "recoil";

/** Interface for the props of the Recoil Observer. */
interface ObserverProps {
  node: RecoilState<any>;
  onChange: (value: any) => void;
}

/**
 * A Recoil Observer used for testing that invokes a callback function when a state is updated.
 * @param props - The props for the RecoilObserver
 * @returns null
 */
export const RecoilObserver: React.FC<ObserverProps> = ({ node, onChange }) => {
  const value = useRecoilValue(node);
  useEffect(() => onChange(value), [onChange, value]);
  return null;
};
